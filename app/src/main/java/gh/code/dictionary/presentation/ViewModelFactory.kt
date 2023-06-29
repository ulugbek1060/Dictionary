package gh.code.dictionary.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gh.code.dictionary.App
import gh.code.dictionary.core.BaseBottomSheetDialogFragment
import gh.code.dictionary.core.BaseFragment

const val ARG_SCREEN = "SCREEN"

class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = app.dictionaryRepository
        val resource = app.resource
        val logger = app.logger
        val commonUi = app.commonUi
        val networkMonitor = app.networkMonitor

        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(
                    repository = repository,
                    resource = resource,
                    logger = logger,
                    commonUi = commonUi,
                    networkMonitor = networkMonitor
                ) as T
            }

            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(
                    repository = repository,
                    resource = resource,
                    commonUi = commonUi
                ) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(
                    repository = repository,
                    resource = resource,
                    logger = logger,
                    commonUi = commonUi
                ) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

inline fun <reified VM : ViewModel> BaseFragment.screenViewModel() = viewModels<VM> {
    val application = requireActivity().application as App
    ViewModelFactory(application)
}

inline fun <reified VM : ViewModel> BaseBottomSheetDialogFragment.screenViewModel() = viewModels<VM> {
    val application = requireActivity().application as App
    ViewModelFactory(application)
}
