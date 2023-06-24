package gh.code.dictionary.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gh.code.dictionary.App
import gh.code.dictionary.R
import gh.code.dictionary.core.BaseScreen
import gh.code.dictionary.core.serializable
import gh.code.dictionary.core.viewBinding
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.databinding.FragmentDetailBinding

class DetailFragment : BottomSheetDialogFragment(R.layout.fragment_detail) {

    class DetailScreen(
        val word: Word
    ) : BaseScreen

    private val binding: FragmentDetailBinding by viewBinding()
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory(requireActivity().application as App)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailScreen = requireArguments().serializable<DetailScreen>(ARG_SCREEN)
        viewModel.setWord(detailScreen?.word)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.word.observe(viewLifecycleOwner) {
            binding.apply {
                tvWord.text = it.word
            }
        }

        viewModel.showError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

}