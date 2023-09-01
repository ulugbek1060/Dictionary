package gh.code.dictionary.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.elevation.SurfaceColors
import gh.code.dictionary.DependencyProvider
import gh.code.dictionary.R
import gh.code.dictionary.core.ARG_SCREEN
import gh.code.dictionary.screens.adapters.AdapterWord
import gh.code.dictionary.core.BaseFragment
import gh.code.dictionary.core.observeEvent
import gh.code.dictionary.screens.adapters.ItemWordView
import gh.code.dictionary.screens.adapters.OnItemClickListener
import gh.code.dictionary.core.screenViewModel
import gh.code.dictionary.utils.textChanges
import gh.code.dictionary.core.viewBinding
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.databinding.FragmentSearchBinding
import gh.code.dictionary.screens.DetailFragment.DetailScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchFragment : BaseFragment(R.layout.fragment_search), OnItemClickListener {

    override val binding by viewBinding<FragmentSearchBinding>()
    override val viewModel by screenViewModel<SearchViewModel> {
        SearchViewModel(
            repository = DependencyProvider.repository,
            resources = DependencyProvider.resources,
            networkMonitor = DependencyProvider.networkMonitor
        )
    }
    private val adapter by lazy { AdapterWord(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.navigationBarColor =
            SurfaceColors.SURFACE_2.getColor(requireContext())

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
        viewModel.message.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.words)
            binding.progressBar.isVisible = state.progress
            binding.containerSearch.isVisible =
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
            viewModel.errorMessage()
            return
        }
        findNavController().navigate(
            resId = R.id.action_searchFragment_to_detailFragment,
            args = bundleOf(ARG_SCREEN to DetailScreen(item))
        )
    }
}