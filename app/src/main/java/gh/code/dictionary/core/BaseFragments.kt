package gh.code.dictionary.core

import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable

interface BaseScreen : Serializable

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

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

