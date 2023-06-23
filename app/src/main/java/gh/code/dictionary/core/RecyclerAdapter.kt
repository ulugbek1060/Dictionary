package gh.code.dictionary.core

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gh.code.dictionary.data.network.models.Word
import gh.code.dictionary.databinding.ItemViewBinding


class RecyclerAdapter : ListAdapter<Word, RecyclerAdapter.ItemViewHolder>(ItemDiffUtil()) {

    class ItemViewHolder(
        private val binding: ItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(word: Word) {
            binding.apply {
                tvPhonetic.text = word.phonetic
                tvWord.text = word.word
                root.setOnClickListener {

                }
            }
        }
    }

    class ItemDiffUtil : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word) =
            oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Word, newItem: Word) =
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