package com.example.thecatapi

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RestClient {

    private  val BASE_URL = "https://api.thecatapi.com/v1/images/"
    private var mRetrofit: Retrofit? = null

    val client: Retrofit
    get() {
        if (mRetrofit == null){
            mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
        return this.mRetrofit!!
    }
}