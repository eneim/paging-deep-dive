package dev.eneim.paging.adapter

import androidx.recyclerview.widget.RecyclerView
import dev.eneim.paging.databinding.HolderItemBinding
import dev.eneim.paging.model.Item

class ItemViewHolder(
    private val binding: HolderItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item?) {
        val content = if (item != null) "Item #${item.index}" else "LOADING"
        binding.item.text = content
    }
}
