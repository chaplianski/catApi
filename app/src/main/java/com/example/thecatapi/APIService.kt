package com.example.thecatapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET ("images? api_key=9ff40de4-c5fc-4524-8783-35ca94d9b13c")
    fun fetchCats (@Query("tagged") tags: String): Call<ListCats>
}