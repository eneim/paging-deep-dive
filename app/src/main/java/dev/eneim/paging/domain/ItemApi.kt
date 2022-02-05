package dev.eneim.paging.domain

import androidx.annotation.IntRange
import dev.eneim.paging.model.Item
import kotlinx.coroutines.delay

object ItemApi {

    const val PAGE_SIZE = 20L
    const val TOTAL_COUNT = 1000L
    const val MIN_PAGE = 1
    const val MAX_PAGE = TOTAL_COUNT / PAGE_SIZE

    private const val SIMULATED_DELAY_MS = 1000L

    suspend fun getPage(
        @IntRange(from = 1) pageNum: Int,
        // For demo purpose, perPage is always PAGE_SIZE.
        @IntRange(from = PAGE_SIZE, to = PAGE_SIZE) perPage: Long
    ): Response<List<Item>> {
        delay(SIMULATED_DELAY_MS) // Fake a network delay.
        // 1 = (1 - 1) * 20 + 1,
        // 21 = (2 - 1) * 20 + 1,
        // 41 = (3 - 1) * 20 + 1, ...
        val startingIndex = pageNum.coerceAtLeast(1).minus(1) * perPage + 1
        val data = (startingIndex until startingIndex + perPage).map(::Item)

        val prevPage = pageNum.minus(1).takeIf { it > 0 } // prev page of page 1 is null.
        val nextPage = pageNum.plus(1).takeIf { it < MAX_PAGE }

        val header = Header(
            previousPage = prevPage,
            nextPage = nextPage,
        )
        return Response(data = data, header = header)
    }

    data class Response<T>(
        val data: T,
        val header: Header,
    )

    data class Header(
        val previousPage: Int? = null,
        val nextPage: Int? = null
    )
}
