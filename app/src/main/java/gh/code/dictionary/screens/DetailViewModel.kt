package gh.code.dictionary.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gh.code.dictionary.R
import gh.code.dictionary.core.AppException
import gh.code.dictionary.core.EmptyFieldException
import gh.code.dictionary.core.Resources
import gh.code.dictionary.core.asLiveData
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.data.repository.DictionaryRepository
import gh.code.dictionary.core.Logger
import gh.code.dictionary.core.MutableLiveEvent
import gh.code.dictionary.core.publishEvent
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: DictionaryRepository,
    private val resources: Resources,
) : ViewModel() {

    private val _word = MutableLiveData<Word>()
    val word = _word.asLiveData()

    private val _message = MutableLiveEvent<String>()
    val message = _message.asLiveData()

    fun setWord(word: Word?) {
        if (word == null) {
            _message.publishEvent(resources.getString(R.string.empty_field))
            return
        }
        _word.value = word!!
        saveToHistory(word)
    }

    fun getAudioUrl(): String? {
        var audio: String? = null
        _word.value?.phonetics?.forEach { phonetic ->
            if (!phonetic.audio.isNullOrBlank()) {
                audio = phonetic.audio
                return@forEach
            }
        }
        if (audio.isNullOrBlank()) {
            _message.publishEvent(resources.getString(R.string.audio_not_found))
        }
        return audio
    }

    private fun saveToHistory(word: Word) = viewModelScope.launch {
        try {
            repository.saveToHistory(word)
        } catch (e: EmptyFieldException) {
            _message.publishEvent(resources.getString(R.string.empty_field))
        } catch (e: AppException) {
            _message.publishEvent(e.message ?: resources.getString(R.string.something_went_wrong))
        }
    }
}