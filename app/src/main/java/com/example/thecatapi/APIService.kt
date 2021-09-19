package com.example.thecatapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET ("search?limit=3&page=100&order=DESC")
    fun fetchCats (@Query("tagged") tags: String): Call<ListCats>
}