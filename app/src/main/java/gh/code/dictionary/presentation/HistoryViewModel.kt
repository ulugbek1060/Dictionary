package gh.code.dictionary.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gh.code.dictionary.core.asLiveData
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.data.repository.DictionaryRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: DictionaryRepository,
) : ViewModel() {

    private val _historyList = MutableLiveData<List<Word>>(emptyList())
    val historyList = _historyList.asLiveData()

    init {
        viewModelScope.launch {
            repository.getHistory().collectLatest {
                _historyList.value = it
            }
        }
    }

    fun clearHistory() {

    }
}