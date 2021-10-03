package com.example.thecatapi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


object RestClient {

    const val API_KEY: String = "9ff40de4-c5fc-4524-8783-35ca94d9b13c"
        private val BASE_URL = "https://api.thecatapi.com/v1/"
    //   private  val BASE_URL = "https://api.github.com"

    private var mRetrofit: Retrofit? = null

    var moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okkHttpclient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(loggingInterceptor
        .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        .setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

        private fun getCatApiClient():APIService {
            val client = Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .build()
            return client.create(APIService::class.java)
        }


    }

