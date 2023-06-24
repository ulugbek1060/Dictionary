package gh.code.dictionary.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gh.code.dictionary.R
import gh.code.dictionary.core.AppException
import gh.code.dictionary.core.ConnectionException
import gh.code.dictionary.core.DataNotFoundException
import gh.code.dictionary.core.Resource
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.data.repository.DictionaryRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: DictionaryRepository,
    private val resource: Resource,
) : ViewModel() {

    private val TAG = "SearchViewModel"

    private val _wordList = MutableLiveData(State())
    val wordList: LiveData<State> get() =  _wordList

    private val _showError = MutableLiveData<String?>(null)
    val showError: LiveData<String?> get() =  _showError

    fun searchWord(word: String) = viewModelScope.launch {
        showProgress()
        try {

            if (word.isEmpty()) {
                clearList()
                return@launch
            }

            val words = repository.searchWord(word)
            clearList()
            _wordList.value = _wordList.value?.copy(
                words = words,
            )
            Log.d(TAG, "searchWord: $words")
        } catch (e: DataNotFoundException) {
            _showError.value = resource.getString(R.string.no_such_word)
        } catch (e: ConnectionException) {
            _showError.value = resource.getString(R.string.no_internet_connection)
        } catch (e: AppException) {
            _showError.value = resource.getString(R.string.something_went_wrong)
        } finally {
            hideProgress()
            _showError.value = null
        }
    }

    private fun showProgress() {
        _wordList.value = _wordList.value?.copy(
            progress = true
        )
    }

    private fun hideProgress() {
        _wordList.value = _wordList.value?.copy(
            progress = false
        )
    }

    private fun clearList() {
        _wordList.value = _wordList.value?.copy(
            words = listOf(),
            progress = false,
        )
    }

    data class State(
        val progress: Boolean = false,
        val words: List<Word> = emptyList(),
    ) {
        val isEmpty: Boolean = words.isEmpty()
    }
}