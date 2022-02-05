package dev.eneim.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import dev.eneim.paging.databinding.HolderItemBinding
import dev.eneim.paging.model.Item

class ItemAdapter : PagingDataAdapter<Item, ItemViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder = ItemViewHolder(
        binding = HolderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(
                oldItem: Item,
                newItem: Item
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: Item,
                newItem: Item
            ): Boolean = oldItem.index == newItem.index
        }
    }
}
