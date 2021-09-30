package com.example.thecatapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.thecatapi.APIService
import com.example.thecatapi.model.Cat

class PostDataSource(private val apiService: APIService) : PagingSource<Int, Cat>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.fetchCats(currentLoadingPageKey)
            val responseCats = mutableListOf<Cat>()
            val cats = response.body()?.cats?: emptyList()
            responseCats.addAll(cats)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseCats,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

}