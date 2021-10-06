package com.example.thecatapi

import androidx.annotation.IntRange
import com.example.thecatapi.model.Cat
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("images/search?limit=8&page=8&order=Desc")

//  @Headers("x-api-key:9ff40de4-c5fc-4524-8783-35ca94d9b13c")
    suspend fun fetchCats(
        @Query("q") query: String? = null,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<List<Cat>>

    companion object {

        private const val BASE_URL = "https://api.thecatapi.com/v1/"
        const val DEFAULT_PAGE_SIZE = 8
        const val MAX_PAGE_SIZE = 8

        fun getApiService(): APIService {
            val url = BASE_URL
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okkHttpclient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(APIService::class.java)
        }
    }
}
