package dev.eneim.paging

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import dev.eneim.paging.adapter.ItemAdapter
import dev.eneim.paging.adapter.ItemViewHolder
import dev.eneim.paging.databinding.ActivityMainBinding
import dev.eneim.paging.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private val viewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter: PagingDataAdapter<Item, ItemViewHolder> = ItemAdapter()
        binding.items.adapter = adapter

        val itemFlow: Flow<PagingData<Item>> = viewModel.items
        lifecycleScope.launchWhenStarted {
            itemFlow.collectLatest { pagingData: PagingData<Item> ->
                adapter.submitData(pagingData)
            }
        }
    }
}
