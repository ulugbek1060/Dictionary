package gh.code.dictionary.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gh.code.dictionary.R
import gh.code.dictionary.core.MutableLiveEvent
import gh.code.dictionary.core.Resources
import gh.code.dictionary.core.asLiveData
import gh.code.dictionary.core.publishEvent
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.data.repository.DictionaryRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: DictionaryRepository,
    private val resources: Resources,
) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.asLiveData()

    private val _message = MutableLiveEvent<String>()
    val message = _message.asLiveData()

    init {
        viewModelScope.launch {
            repository.getHistory().collectLatest {
                _state.value = _state.value?.copy(
                    list = it,
                    isListEmpty = it.isEmpty()
                )
            }
        }
    }

    fun removeItem(word: Word) {
        viewModelScope.launch {
            repository.removeFromHistory(word)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
            _message.publishEvent(resources.getString(R.string.history_cleared_succesfully))
        }
    }

    fun errorMessage() {
        _message.publishEvent(resources.getString(R.string.item_not_found))
    }

    data class State(
        val list: List<Word> = emptyList(),
        val isListEmpty: Boolean = false
    )
}