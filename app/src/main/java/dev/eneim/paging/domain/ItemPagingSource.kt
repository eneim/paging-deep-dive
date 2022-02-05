package dev.eneim.paging.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.eneim.paging.domain.ItemApi.PAGE_SIZE
import dev.eneim.paging.model.Item

class ItemPagingSource : PagingSource<Int, Item>() {

    // Copied from https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data#pagingsource
    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        val anchor = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchor)
        return anchorPage?.prevKey?.plus(1)
            ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: ItemApi.MIN_PAGE
        return try {
            val response = ItemApi.getPage(pageNum = page, perPage = PAGE_SIZE)
            LoadResult.Page(
                data = response.data,
                prevKey = response.header.previousPage,
                nextKey = response.header.nextPage,
            )
        } catch (error: Throwable) {
            error.printStackTrace()
            LoadResult.Error(throwable = error)
        }
    }
}
