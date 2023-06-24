package gh.code.dictionary.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import gh.code.dictionary.R
import gh.code.dictionary.core.RecyclerAdapter
import gh.code.dictionary.core.viewBinding
import gh.code.dictionary.databinding.FragmentSearchBinding
import gh.code.dictionary.core.BaseFragment
import gh.code.dictionary.core.OnItemClickListener
import gh.code.dictionary.core.RecyclerItemView
import gh.code.dictionary.core.textChanges
import gh.code.dictionary.presentation.DetailFragment.DetailScreen
import gh.code.dictionary.data.network.models.Word
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchFragment : BaseFragment(R.layout.fragment_search), OnItemClickListener {

    override val binding by viewBinding<FragmentSearchBinding>()
    override val viewModel by screenViewModel<SearchViewModel>()
    private val adapter by lazy { RecyclerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchWord()
        observeList()
        showErrors()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

        binding.recyclerView.adapter = adapter
    }

    private fun showErrors() {
        viewModel.showError.observe(viewLifecycleOwner) {
            if (it != null) Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeList() {
        viewModel.wordList.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.words)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun searchWord() {
        binding.edittext.textChanges().debounce(500).onEach { str ->
            viewModel.searchWord(str.toString())
        }.launchIn(lifecycleScope)
    }

    override fun onClick(item: RecyclerItemView) {
        if (item is Word) {
            findNavController().navigate(
                resId = R.id.action_searchFragment_to_detailFragment,
                args = bundleOf(ARG_SCREEN to DetailScreen(item))
            )
        }
        else {
            Toast.makeText(requireContext(), "Item is not correct!", Toast.LENGTH_SHORT).show()
        }
    }

}