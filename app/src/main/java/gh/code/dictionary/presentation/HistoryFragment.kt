package gh.code.dictionary.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import gh.code.dictionary.R
import gh.code.dictionary.core.viewBinding
import gh.code.dictionary.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val binding by viewBinding<FragmentHistoryBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}