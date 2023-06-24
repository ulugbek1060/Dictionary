package gh.code.dictionary.presentation

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import gh.code.dictionary.R
import gh.code.dictionary.core.AdapterMeaning
import gh.code.dictionary.core.BaseBottomSheetDialogFragment
import gh.code.dictionary.core.BaseScreen
import gh.code.dictionary.core.playFromUrl
import gh.code.dictionary.core.serializable
import gh.code.dictionary.core.viewBinding
import gh.code.dictionary.data.network.models.Meaning
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.databinding.FragmentDetailBinding

class DetailFragment : BaseBottomSheetDialogFragment(R.layout.fragment_detail) {

    class DetailScreen(
        val word: Word
    ) : BaseScreen

    override val binding: FragmentDetailBinding by viewBinding()
    override val viewModel by screenViewModel<DetailViewModel>()
    private lateinit var adapter: AdapterMeaning

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailScreen = requireArguments().serializable<DetailScreen>(ARG_SCREEN)
        viewModel.setWord(detailScreen?.word)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWord()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSound.setOnClickListener {
            val imageView = ((it as LinearLayout).children.first() as ImageView)
            imageView.playFromUrl(viewModel.getAudioUrl()) {
                prepareAsync()
            }
        }
        binding.btnShare.setOnClickListener {

        }
        //        viewModel.showError.observe(viewLifecycleOwner) {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//        }
    }

    private fun setupRecyclerView(meanings: List<Meaning>) {
        binding.apply {
            adapter = AdapterMeaning(meanings)
            recyclerViewMeanings.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            recyclerViewMeanings.adapter = adapter
        }
    }

    private fun setupWord() {
        viewModel.word.observe(viewLifecycleOwner) {
            binding.apply {
                tvWord.text = it.word
                tvPhonetic.text = it.phonetic
                setupRecyclerView(it.meanings ?: listOf())
            }
        }
    }
}