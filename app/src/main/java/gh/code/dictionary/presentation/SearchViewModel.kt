package gh.code.dictionary.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gh.code.dictionary.R
import gh.code.dictionary.core.AppException
import gh.code.dictionary.core.CommonUi
import gh.code.dictionary.core.ConnectionException
import gh.code.dictionary.core.DataNotFoundException
import gh.code.dictionary.core.Resource
import gh.code.dictionary.core.asLiveData
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.data.repository.DictionaryRepository
import gh.code.dictionary.utils.Logger
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: DictionaryRepository,
    private val resource: Resource,
    private val commonUi: CommonUi,
    private val logger: Logger,
) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.asLiveData()

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
                logger.log(words)
            } catch (e: DataNotFoundException) {
                commonUi.toast(resource.getString(R.string.no_such_word))
                logger.err(e)
            } catch (e: ConnectionException) {
                commonUi.toast(resource.getString(R.string.no_internet_connection))
                logger.err(e)
            } catch (e: AppException) {
                commonUi.toast(resource.getString(R.string.something_went_wrong))
                logger.err(e)
            } finally {
                hideProgress()
            }
        }
    }

    fun showErrorItemNotFound() {
        commonUi.toast(resource.getString(R.string.item_not_found))
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
    ) {
        val isEmpty: Boolean = words.isEmpty()
    }
}