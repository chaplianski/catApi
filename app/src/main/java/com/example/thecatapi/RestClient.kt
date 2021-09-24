package com.example.thecatapi

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RestClient {

    const val API_KEY: String = "9ff40de4-c5fc-4524-8783-35ca94d9b13c"
    private val BASE_URL = "https://thecatapi.com/v1/"
    //   private  val BASE_URL = "https://api.github.com"

    private var mRetrofit: Retrofit? = null


    //   var gson = GsonBuilder()
//        .setLenient()
//       .create()
    var moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()


    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }



    val okkHttpclient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(loggingInterceptor)
        .build()

        @ExperimentalSerializationApi
        val client: Retrofit
            get() {


                if (mRetrofit == null) {
                    mRetrofit = Retrofit.Builder()
                        .client(okkHttpclient)
                        .baseUrl(BASE_URL)
                        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                        .build()

                }
                return this.mRetrofit!!
            }
    }

