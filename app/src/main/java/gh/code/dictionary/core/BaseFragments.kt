package gh.code.dictionary.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseFragment(
    @LayoutRes id: Int
) : Fragment(id) {
    abstract val viewModel: ViewModel
    abstract val binding: ViewBinding
}

abstract class BaseBottomSheetDialogFragment(
    @LayoutRes id: Int
) : BottomSheetDialogFragment(id) {
    abstract val viewModel: ViewModel
    abstract val binding: ViewBinding
}

