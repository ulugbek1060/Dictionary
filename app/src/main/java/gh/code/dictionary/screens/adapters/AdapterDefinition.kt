package gh.code.dictionary.screens.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gh.code.dictionary.databinding.ItemDefinitionViewBinding

interface ItemDefinitionView {
    val itemDefinition: String?
    val itemExample: String?
}

class AdapterDefinition(
    private val list: List<ItemDefinitionView>
) : RecyclerView.Adapter<AdapterDefinition.DefinitionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder =
        DefinitionViewHolder(
            binding = ItemDefinitionViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    class DefinitionViewHolder(
        private val binding: ItemDefinitionViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: ItemDefinitionView, position: Int) {
            binding.apply {
                tvPosition.text = "${position + 1}"
                tvDefinition.text = item.itemDefinition
                tvExample.text = item.itemExample
            }
        }
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

}