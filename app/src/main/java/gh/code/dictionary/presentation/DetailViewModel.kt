package gh.code.dictionary.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gh.code.dictionary.R
import gh.code.dictionary.core.AppException
import gh.code.dictionary.core.EmptyFieldException
import gh.code.dictionary.core.Resource
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.data.repository.DictionaryRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: DictionaryRepository, private val resource: Resource
) : ViewModel() {

    private val _showError = MutableLiveData<String?>(null)
    val showError: LiveData<String?> get() = _showError

    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word> get() = _word

    fun setWord(word: Word?) {
        if (word == null) {
            _showError.value = resource.getString(R.string.empty_field)
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
            _showError.value = resource.getString(R.string.audio_not_found)
        }
        return audio
    }

    private fun saveToHistory(word: Word) = viewModelScope.launch {
        try {
            repository.saveToHistory(word)
        } catch (e: EmptyFieldException) {
            _showError.value = resource.getString(R.string.empty_field)
        } catch (e: AppException) {
            _showError.value = e.message
        }
    }
}