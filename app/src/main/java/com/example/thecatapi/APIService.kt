package com.example.thecatapi

import com.example.thecatapi.model.Cat
import com.example.thecatapi.model.Cats
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


interface APIService {

  @GET ("images/search?limit=100&page=8&order=Desc")
//  @Headers("x-api-key:9ff40de4-c5fc-4524-8783-35ca94d9b13c")
/*  suspend fun everything (
      @Query("q") query: String? = null,
      @Query ("page") @IntRange (from = 1) page: Int,
      @Query ("pageSize") @IntRange (from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE


  ): Response<Cat>
*/

  fun fetchCats(@Query("page") pageNumber: Int): Call<List<Cat>>

    companion object {

        private val BASE_URL = "https://api.thecatapi.com/v1/"

             private val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .build()

             fun getApiService() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okkHttpclient)
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())

                .build()
                .create(APIService::class.java)

    }

}


