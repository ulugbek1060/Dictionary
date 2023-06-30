package gh.code.dictionary.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gh.code.dictionary.R
import gh.code.dictionary.core.AppException
import gh.code.dictionary.core.CommonUi
import gh.code.dictionary.core.EmptyFieldException
import gh.code.dictionary.core.Resource
import gh.code.dictionary.core.asLiveData
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.data.repository.DictionaryRepository
import gh.code.dictionary.utils.Logger
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: DictionaryRepository,
    private val resource: Resource,
    private val logger: Logger,
    private val commonUi: CommonUi
) : ViewModel() {

    private val _word = MutableLiveData<Word>()
    val word = _word.asLiveData()

    fun setWord(word: Word?) {
        if (word == null) {
            commonUi.toast(resource.getString(R.string.empty_field))
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
            commonUi.toast(resource.getString(R.string.audio_not_found))
            logger.err(resource.getString(R.string.audio_not_found))
        }
        return audio
    }

    private fun saveToHistory(word: Word) = viewModelScope.launch {
        try {
            repository.saveToHistory(word)
        } catch (e: EmptyFieldException) {
            commonUi.toast(resource.getString(R.string.empty_field))
            logger.err(e)
        } catch (e: AppException) {
            commonUi.toast(e.message ?: resource.getString(R.string.something_went_wrong))
            logger.err(e)
        }
    }
}