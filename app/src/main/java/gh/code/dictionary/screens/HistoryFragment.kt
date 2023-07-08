package gh.code.dictionary.screens

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import gh.code.dictionary.R
import gh.code.dictionary.core.AdapterWord
import gh.code.dictionary.core.BaseFragment
import gh.code.dictionary.DependencyProvider
import gh.code.dictionary.core.ARG_SCREEN
import gh.code.dictionary.core.ItemWordView
import gh.code.dictionary.core.OnItemClickListener
import gh.code.dictionary.core.WITH_REMOVE_BUTTON
import gh.code.dictionary.core.screenViewModel
import gh.code.dictionary.core.viewBinding
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.databinding.FragmentHistoryBinding
import gh.code.dictionary.screens.DetailFragment.DetailScreen

class HistoryFragment : BaseFragment(R.layout.fragment_history), OnItemClickListener {


    override val binding by viewBinding<FragmentHistoryBinding>()
    override val viewModel by screenViewModel<HistoryViewModel> {
        HistoryViewModel(
            repository = DependencyProvider.repository,
            resource = DependencyProvider.resource,
            commonUi = DependencyProvider.commonUi
        )
    }
    private val adapter by lazy {
        AdapterWord(
            itemClickListener = this,
            buttonsFlag = WITH_REMOVE_BUTTON
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        setupRecyclerView()
        setupListeners()
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.list)
            topBarMenuVisibility(state.isListEmpty)
            binding.recyclerView.isVisible = !state.isListEmpty
            binding.containerEmpty.isVisible = state.isListEmpty
        }
    }

    private fun topBarMenuVisibility(isEmpty: Boolean) {
        val item = binding.topAppBar.menu.findItem(R.id.clear)
        item.isVisible = !isEmpty
        binding.topAppBar.invalidateMenu()
    }

    private fun setupListeners() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.clear -> {
                    viewModel.clearHistoryDialog()
                    true
                }

                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerView.adapter = adapter
    }

    override fun onClick(item: ItemWordView) {
        if (item !is Word) {
            viewModel.errorMessage()
            return
        }
        findNavController().navigate(
            resId = R.id.action_historyFragment_to_detailFragment,
            args = bundleOf(ARG_SCREEN to DetailScreen(item))
        )
    }

    override fun removeItem(item: ItemWordView) {
        if (item !is Word) {
            viewModel.errorMessage()
            return
        }
        viewModel.removeItem(item)
    }
}