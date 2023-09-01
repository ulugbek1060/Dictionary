package gh.code.dictionary.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gh.code.dictionary.R
import gh.code.dictionary.core.AppException
import gh.code.dictionary.core.ConnectionException
import gh.code.dictionary.core.DataNotFoundException
import gh.code.dictionary.core.Resources
import gh.code.dictionary.core.asLiveData
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.data.repository.DictionaryRepository
import gh.code.dictionary.core.Logger
import gh.code.dictionary.core.MutableLiveEvent
import gh.code.dictionary.core.publishEvent
import gh.code.dictionary.utils.NetworkMonitor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: DictionaryRepository,
    private val resources: Resources,
    private val networkMonitor: NetworkMonitor,
) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.asLiveData()

    private val _message = MutableLiveEvent<String>()
    val message = _message.asLiveData()

    init {
        viewModelScope.launch {
            networkMonitor.isOnline.collectLatest {
                _state.value = _state.value?.copy(
                    hasConnection = it
                )
            }
        }
    }

    private var _currentWord: String = ""

    fun getWord(word: String) {
        if (_currentWord == word) return
        _currentWord = word

        viewModelScope.launch {
            showProgress()
            try {

                if (word.isEmpty()) {
                    clearList()
                    return@launch
                }

                val words = repository.searchWord(word)
                clearList()
                _state.value = _state.value?.copy(
                    words = words,
                )
            } catch (e: DataNotFoundException) {
                _message.publishEvent(resources.getString(R.string.no_such_word))
            } catch (e: ConnectionException) {
                _message.publishEvent(resources.getString(R.string.no_internet_connection))
            } catch (e: AppException) {
                _message.publishEvent(resources.getString(R.string.something_went_wrong))
            } finally {
                hideProgress()
            }
        }
    }

    fun errorMessage() {
        _message.publishEvent(resources.getString(R.string.item_not_found))
    }

    private fun showProgress() {
        _state.value = _state.value?.copy(
            progress = true
        )
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(
            progress = false
        )
    }

    private fun clearList() {
        _state.value = _state.value?.copy(
            words = listOf(),
            progress = false,
        )
    }

    data class State(
        val progress: Boolean = false,
        val words: List<Word> = emptyList(),
        val hasConnection: Boolean = false
    ) {
        val isListEmpty: Boolean = words.isEmpty()
    }
}