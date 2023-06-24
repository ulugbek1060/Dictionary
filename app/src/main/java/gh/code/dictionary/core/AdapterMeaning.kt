package gh.code.dictionary.core

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import gh.code.dictionary.databinding.ItemMeaningViewBinding

interface ItemMeaningView {
    val itemPartOfSpeech: String?
    val itemAntonyms: List<String>?
    val itemSynonyms: List<String>?
    val itemDefinitions: List<ItemDefinitionView>
}

class AdapterMeaning(
    private val list: List<ItemMeaningView>,
) : RecyclerView.Adapter<AdapterMeaning.MeaningViewHolder>() {

    inner class MeaningViewHolder(
        private val binding: ItemMeaningViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: ItemMeaningView, position: Int) {
            binding.apply {
                tvPartOfSpeech.text = "${position + 1}. ${item.itemPartOfSpeech}"

                setupAntonyms(item)
                setupSynonyms(item)
                setupDefinitions(item)

            }
        }

        private fun setupDefinitions(item: ItemMeaningView) {
            binding.apply {
                val isVisible = item.itemDefinitions.isEmpty()
                containerDefinitions.isVisible = !isVisible
                if (!isVisible)
                    recyclerViewDefinitions.adapter = AdapterDefinition(
                        list = item.itemDefinitions
                    )
            }
        }

        private fun setupSynonyms(item: ItemMeaningView) {
            binding.apply {
                val isVisible = item.itemSynonyms.isNullOrEmpty()
                containerSynonyms.isVisible = !isVisible
                if (!isVisible)
                    recyclerSynonyms.adapter = AdapterText(
                        list = item.itemSynonyms ?: listOf()
                    )
            }
        }

        private fun setupAntonyms(item: ItemMeaningView) {
            binding.apply {
                val isVisible = item.itemAntonyms.isNullOrEmpty()
                containerAntonyms.isVisible = !isVisible
                if (!isVisible)
                    recyclerAntonyms.adapter =
                        AdapterText(item.itemAntonyms ?: listOf())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder =
        MeaningViewHolder(
            binding = ItemMeaningViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }
}