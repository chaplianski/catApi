package com.example.thecatapi.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.thecatapi.APIService
import com.example.thecatapi.CatsAdapter
import com.example.thecatapi.model.Cat
import retrofit2.Call
import retrofit2.Response

lateinit var call: Call<List<Cat>>
class PostDataSource(private val apiService: APIService) : PagingSource<Int, Cat>() {
  //  var call = apiService.fetchCats(1)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        try {
            val currentLoadingPageKey = params.key ?: 1
 //           val response = apiService.fetchCats(currentLoadingPageKey)
            call = apiService.fetchCats(currentLoadingPageKey)
            val responseCats = mutableListOf<Cat>()
            val cats = fetchCatList()

 //           val cats = call.body()
                //?.cats//?: emptyList()
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

    private fun fetchCatList(): List<Cat> {
      //  var mApiService: APIService? = null
        val mAdapter: CatsAdapter? = null
       //call = mApiService?.fetchCats()
    //    var call = mApiService?.fetchCats(1)
            var allCats: MutableList<Cat> = ArrayList()


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
        return allCats
    }

}