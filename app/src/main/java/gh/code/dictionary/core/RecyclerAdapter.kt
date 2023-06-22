package gh.code.dictionary.core

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gh.code.dictionary.data.models.ResponseWord
import gh.code.dictionary.databinding.ItemViewBinding


class RecyclerAdapter : ListAdapter<ResponseWord, RecyclerAdapter.ItemViewHolder>(ItemDiffUtil()) {

    class ItemViewHolder(
        private val binding: ItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(responseWord: ResponseWord) {
            binding.apply {
                tvPhonetic.text = responseWord.phonetic
                tvWord.text = responseWord.word
                root.setOnClickListener {

                }
            }
        }
    }

    class ItemDiffUtil : DiffUtil.ItemCallback<ResponseWord>() {
        override fun areItemsTheSame(oldItem: ResponseWord, newItem: ResponseWord) =
            oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ResponseWord, newItem: ResponseWord) =
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