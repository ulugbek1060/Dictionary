package gh.code.dictionary.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment(@LayoutRes id: Int) : Fragment(id) {
    abstract val viewModel: ViewModel
    abstract val binding: ViewBinding
}