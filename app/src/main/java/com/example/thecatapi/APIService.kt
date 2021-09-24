package com.example.thecatapi

import retrofit2.Call
import retrofit2.http.*


interface APIService {

 //   @Headers(*["Accept: application/json"])
//    @POST(SyncStateContract.Constants.Api.URL_REGISTRATION)
//    @FormUrlEncoded
 //   @Header(X-Api-Key: "${RestClient.API_KEY}")
 //   @Headers("Content-Type: application/json")
 //   @GET ("/v1/images? api_key=9ff40de4-c5fc-4524-8783-35ca94d9b13c")
///    @GET ("images? api_key=${RestClient.API_KEY}")
 //   @Headers("x-api-key: 9ff40de4-c5fc-4524-8783-35ca94d9b13c")
 //   @GET ("search?limit=10&page=10&order=Desc")
    @GET ("images/search?size=small&order=RANDOM&limit=5&format=json")

    fun fetchCats():  Call<List<Cat>>
   //          (@Query("id") id: String, @Query("url") url: String )

 //   fun fetchCats (@Query("tagged") tags: String): Call<ListCats>


}
