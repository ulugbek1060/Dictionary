package gh.code.dictionary.core

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

const val ARG_SCREEN = "SCREEN"

typealias ViewModelCreator<VM> = () -> VM

class ViewModelFactory<VM : ViewModel>(
    private val viewModelCreator: ViewModelCreator<VM>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelCreator() as T
    }
}

inline fun <reified VM : ViewModel> BaseFragment.screenViewModel(
    noinline viewModelCreator: ViewModelCreator<VM>
) = viewModels<VM> {
    ViewModelFactory(viewModelCreator)
}

inline fun <reified VM : ViewModel> BaseBottomSheetDialogFragment.screenViewModel(
    noinline viewModelCreator: ViewModelCreator<VM>
) = viewModels<VM> {
    ViewModelFactory(viewModelCreator)
}

