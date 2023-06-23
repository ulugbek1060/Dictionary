package gh.code.dictionary.core

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gh.code.dictionary.App
import gh.code.dictionary.presentation.SearchViewModel

//const val ARG_SCREEN = "SCREEN"

class ViewModelFactory(
//    private val screen: BaseScreen,
    private val app: App
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = app.dictionaryRepository
        val resource = app.resource
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(
                    repository = repository,
                    resource = resource
                ) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

inline fun <reified VM : ViewModel> BaseFragment.screenViewModel() = viewModels<VM> {
    val application = requireActivity().application as App
//    val screen = requireArguments().getSerializable(ARG_SCREEN) as BaseScreen
    ViewModelFactory(
//        screen,
        application
    )
}