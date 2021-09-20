package com.example.thecatapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RestClient {

    private  val BASE_URL = "https://thecatapi.com/v1/"
    private var mRetrofit: Retrofit? = null

    val client: Retrofit
    get() {
        if (mRetrofit == null){
            mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return this.mRetrofit!!
    }
}