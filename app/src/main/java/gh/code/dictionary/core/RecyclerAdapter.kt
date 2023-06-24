package gh.code.dictionary.core

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.databinding.ItemViewBinding

interface RecyclerItemView {
    val itemWord: String?
    val itemPhonetic: String?
}

interface OnItemClickListener {
    fun onClick(item: RecyclerItemView)
}

class RecyclerAdapter(
    private val itemClickListener: OnItemClickListener
) : ListAdapter<RecyclerItemView, RecyclerAdapter.ItemViewHolder>(ItemDiffUtil()) {

    inner class ItemViewHolder(
        private val binding: ItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(itemView: RecyclerItemView) {
            binding.apply {
                tvPhonetic.text = itemView.itemPhonetic
                tvWord.text = itemView.itemWord
                root.setOnClickListener {
                    itemClickListener.onClick(itemView)
                }
            }
        }
    }

    class ItemDiffUtil : DiffUtil.ItemCallback<RecyclerItemView>() {
        override fun areItemsTheSame(oldItem: RecyclerItemView, newItem: RecyclerItemView) =
            oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: RecyclerItemView, newItem: RecyclerItemView) =
            oldItem === newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}