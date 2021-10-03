package com.example.thecatapi.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.thecatapi.APIService
import com.example.thecatapi.model.Cat
import com.example.thecatapi.model.toCat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException



class PostDataSource(private val apiService: APIService, private val query: String) : PagingSource<Int, Cat>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
      if (query.isBlank()) {
          return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
      }
         try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize.coerceAtMost(APIService.MAX_PAGE_SIZE)

            val currentLoadingPageKey = params.key ?: 1
            val call = apiService.fetchCats(query, pageNumber, pageSize)
            if (call.isSuccessful){
                val allcats = call.body()!!.map { it.toCat() }
                Log.d("MyLog", "Successfull List:${allcats}")
                val nextPageNumber = if (allcats.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null

          //      val nextPageNumber = if (allcats?.isEmpty() == true) null else currentLoadingPageKey + 1
         //       val prevPageNumber = if (currentLoadingPageKey > 1) currentLoadingPageKey - 1 else null
                return LoadResult.Page(allcats, prevPageNumber, nextPageNumber)

            }else {
                return LoadResult.Error(HttpException(call))
            }

  //          val responceCats = allcats?.url.toString()
  //          val listUrlCat = mutableListOf<String>()
  //          listUrlCat.add(responceCats)
  //         val responseCats = mutableListOf<Cat>()
//            val cattt =

 //           responseCats.addAll(allcats)

  //          val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1




        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
/*
    private fun fetchCatList(): List<Cat> {
      //  var mApiService: APIService? = null
        val mAdapter: CatsAdapter? = null
       //call = mApiService?.fetchCats()
    //    var call = mApiService?.fetchCats(1)
            var allCats: MutableList<Cat> = ArrayList()

        CoroutineScope(Dispatchers.IO).launch {
         call?.enqueue(object: retrofit2.Callback <List<Cat>> {

            override fun onResponse (call: Call<List<Cat>>, response: Response<List<Cat>>) {
                val catResponse = response.body()
                Log.d("MyLog", "${response.body()}")

                if (catResponse == null){
                    mAdapter!!.notifyDataSetChanged()
                }
                if (catResponse != null) {
                    allCats.addAll(catResponse)
                    Log.d("MyLog", "${allCats}")
                    mAdapter!!.notifyDataSetChanged()
     //               val participantJsonList: List<Skill> =
    //                    mapper.readValue(jsonString, object : TypeReference<List<Cat?>?>() {})
                }
            }
            override fun onFailure (call : Call<List<Cat>>, t: Throwable){

                Log.d("MyLog", "Error: ${t.localizedMessage}")
            }

        })
         //   return@launch allCats as Unit
        }
        return allCats
    }*/

    companion object {

        const val INITIAL_PAGE_NUMBER = 1
    }

}