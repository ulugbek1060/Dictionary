package gh.code.dictionary.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gh.code.dictionary.R
import gh.code.dictionary.core.AlertDialogConfig
import gh.code.dictionary.core.CommonUi
import gh.code.dictionary.core.Resource
import gh.code.dictionary.core.asLiveData
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.data.repository.DictionaryRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: DictionaryRepository,
    private val resource: Resource,
    private val commonUi: CommonUi,
) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.asLiveData()

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

    fun clearHistoryDialog() {
        viewModelScope.launch {
            val result: Boolean = commonUi.alertDialog(
                AlertDialogConfig(
                    title = resource.getString(R.string.confirmation),
                    message = resource.getString(R.string.are_you_sure_to_clear_all_history),
                    negativeButton = resource.getString(R.string.no),
                    positiveButton = resource.getString(R.string.yes)
                )
            )
            if (result) clearHistory()
        }
    }

    private fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
            commonUi.toast(resource.getString(R.string.history_cleared_succesfully))
        }
    }

    fun errorMessage() {
        commonUi.toast(resource.getString(R.string.item_not_found))
    }

    data class State(
        val list: List<Word> = emptyList(),
        val isListEmpty: Boolean = false
    )
}