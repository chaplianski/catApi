package com.example.thecatapi.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.thecatapi.APIService
import com.example.thecatapi.model.Cat
import com.example.thecatapi.model.toCat
import retrofit2.HttpException

class PostDataSource(private val apiService: APIService, private val query: String) : PagingSource<Int, Cat>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        if (query.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize.coerceAtMost(APIService.MAX_PAGE_SIZE)

            val call = apiService.fetchCats(query, pageNumber, pageSize)
            if (call.isSuccessful) {
                val allcats = call.body()!!.map { it.toCat() }
                Log.d("MyLog", "Successfull List:$allcats")
                val nextPageNumber = if (allcats.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null

                return LoadResult.Page(allcats, prevPageNumber, nextPageNumber)
            } else {
                return LoadResult.Error(HttpException(call))
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    companion object {

        const val INITIAL_PAGE_NUMBER = 1
    }
}
