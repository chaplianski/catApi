package com.example.thecatapi

import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


object RestClient {

    const val API_KEY: String = "9ff40de4-c5fc-4524-8783-35ca94d9b13c"
     private  val BASE_URL = "https://thecatapi.com/v1/"
 //   private  val BASE_URL = "https://api.github.com"

    private var mRetrofit: Retrofit? = null

    var gson = GsonBuilder()
        .setLenient()
        .create()

    val client: Retrofit
    get() {
        if (mRetrofit == null){
            mRetrofit = Retrofit.Builder()

                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        }
        return this.mRetrofit!!
    }
}

