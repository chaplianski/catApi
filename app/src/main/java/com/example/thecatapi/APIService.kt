package com.example.thecatapi

import com.example.thecatapi.RestClient.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService {


 //   @Header(X-Api-Key: "${RestClient.API_KEY}")
    @Headers("Content-Type: application/json")
 //   @GET ("/v1/images? api_key=9ff40de4-c5fc-4524-8783-35ca94d9b13c")
///    @GET ("images? api_key=${RestClient.API_KEY}")
       @GET ("images/search/")
    fun fetchCats (): Call<List<Cat>>
 //   fun fetchCats (@Query("tagged") tags: String): Call<ListCats>
}
