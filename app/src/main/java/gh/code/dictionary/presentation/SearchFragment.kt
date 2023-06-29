package gh.code.dictionary.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import gh.code.dictionary.R
import gh.code.dictionary.core.AdapterWord
import gh.code.dictionary.core.BaseFragment
import gh.code.dictionary.core.ItemWordView
import gh.code.dictionary.core.OnItemClickListener
import gh.code.dictionary.core.textChanges
import gh.code.dictionary.core.viewBinding
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.databinding.FragmentSearchBinding
import gh.code.dictionary.presentation.DetailFragment.DetailScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchFragment : BaseFragment(R.layout.fragment_search), OnItemClickListener {

    override val binding by viewBinding<FragmentSearchBinding>()
    override val viewModel by screenViewModel<SearchViewModel>()
    private val adapter by lazy { AdapterWord(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        searchWord()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerView.adapter = adapter
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.words)
            binding.progressBar.isVisible = state.progress
            binding.ivSearch.isVisible =
                state.isListEmpty && !state.progress && state.hasConnection
            binding.ivConnection.isVisible = !state.hasConnection
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun searchWord() {
        binding.edittext
            .textChanges()
            .debounce(500)
            .onEach { str ->
                viewModel.getWord(str.toString())
            }
            .launchIn(lifecycleScope)
    }

    override fun onClick(item: ItemWordView) {
        if (item !is Word) {
            viewModel.showErrorItemNotFound()
            return
        }
        findNavController().navigate(
            resId = R.id.action_searchFragment_to_detailFragment,
            args = bundleOf(ARG_SCREEN to DetailScreen(item))
        )
    }
}