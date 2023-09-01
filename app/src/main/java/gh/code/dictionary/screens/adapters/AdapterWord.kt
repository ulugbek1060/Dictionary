package gh.code.dictionary.screens.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gh.code.dictionary.databinding.ItemWordViewBinding

interface ItemWordView {
    val itemWord: String?
    val itemPhonetic: String?
}

interface OnItemClickListener {
    fun onClick(item: ItemWordView)
    fun removeItem(item: ItemWordView) = Unit
}

const val WITH_REMOVE_BUTTON = 1
const val WITHOUT_REMOVE_BUTTON = 0

class AdapterWord(
    private val itemClickListener: OnItemClickListener,
    private val buttonsFlag: Int = WITHOUT_REMOVE_BUTTON
) : ListAdapter<ItemWordView, AdapterWord.ItemViewHolder>(ItemDiffUtil()) {

    inner class ItemViewHolder(
        private val binding: ItemWordViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(itemView: ItemWordView) {
            binding.apply {
                removeItem.isVisible = buttonsFlag == WITH_REMOVE_BUTTON
                tvPhonetic.text = itemView.itemPhonetic
                tvWord.text = itemView.itemWord
                root.setOnClickListener {
                    itemClickListener.onClick(itemView)
                }
                removeItem.setOnClickListener {
                    itemClickListener.removeItem(itemView)
                }
            }
        }
    }

    class ItemDiffUtil : DiffUtil.ItemCallback<ItemWordView>() {
        override fun areItemsTheSame(oldItem: ItemWordView, newItem: ItemWordView) =
            oldItem.toString() == newItem.toString()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ItemWordView, newItem: ItemWordView) =
            oldItem === newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            ItemWordViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}