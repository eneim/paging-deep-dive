package dev.eneim.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.eneim.paging.domain.ItemPagingSource
import dev.eneim.paging.model.Item
import kotlinx.coroutines.flow.Flow

class ItemViewModel : ViewModel() {

    val items: Flow<PagingData<Item>> = Pager(
        config = PagingConfig(
            pageSize = 10, // This pageSize can be different to the value used by PagingSource.
            enablePlaceholders = true,
        ),
        initialKey = null,
        pagingSourceFactory = ::ItemPagingSource,
    ).flow.cachedIn(viewModelScope)
}
