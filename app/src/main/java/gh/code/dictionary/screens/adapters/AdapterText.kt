package gh.code.dictionary.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gh.code.dictionary.databinding.ItemTextViewBinding

class AdapterText(
    private val list: List<String>
) : RecyclerView.Adapter<AdapterText.TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder =
        TextViewHolder(
            binding = ItemTextViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    class TextViewHolder(
        private val binding: ItemTextViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: String) {
            binding.root.text = item

        }
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

}