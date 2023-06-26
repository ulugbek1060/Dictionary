package gh.code.dictionary.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import gh.code.dictionary.R
import gh.code.dictionary.core.AdapterWord
import gh.code.dictionary.core.BaseFragment
import gh.code.dictionary.core.ItemWordView
import gh.code.dictionary.core.OnItemClickListener
import gh.code.dictionary.core.viewBinding
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.databinding.FragmentHistoryBinding
import gh.code.dictionary.presentation.DetailFragment.DetailScreen

class HistoryFragment : BaseFragment(R.layout.fragment_history), OnItemClickListener {

    override val viewModel by screenViewModel<HistoryViewModel>()
    override val binding by viewBinding<FragmentHistoryBinding>()
    private val adapter by lazy { AdapterWord(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        viewModel.historyList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerView.adapter = adapter
    }

    override fun onClick(item: ItemWordView) {
        if (item is Word) {
            findNavController().navigate(
                resId = R.id.action_historyFragment_to_detailFragment,
                args = bundleOf(ARG_SCREEN to DetailScreen(item))
            )
        } else {
            Toast.makeText(
                requireContext(),
                "Item is not correct!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}