package com.example.thecatapi

import androidx.annotation.IntRange
import com.example.thecatapi.model.Cat
import com.example.thecatapi.model.Cats
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


interface APIService {

  @GET ("images/search?limit=8&page=8&order=Desc")

//  @Headers("x-api-key:9ff40de4-c5fc-4524-8783-35ca94d9b13c")
    suspend fun fetchCats(
      @Query("q") query: String? = null,
      @Query("page") @IntRange(from = 1) page: Int = 1,
      @Query("pageSize") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<List<Cat>>

    companion object {

        private val BASE_URL = "https://api.thecatapi.com/v1/"
        const val DEFAULT_PAGE_SIZE = 8
        const val MAX_PAGE_SIZE = 8


             fun getApiService() : APIService {
                 val url = BASE_URL
                 val moshi = Moshi.Builder()
                     .add(KotlinJsonAdapterFactory())
                     .build()

             //    val type: Type = object : TypeToken<List<Map<String?, Float?>?>?>() {}.getType()
             //    val myList: List<Map<String, Float>> = gson.fromJson(output, type)


                 val loggingInterceptor = HttpLoggingInterceptor().apply {
                     level = HttpLoggingInterceptor.Level.BODY
                 }

                 val okkHttpclient = OkHttpClient.Builder()
                     .addInterceptor(loggingInterceptor)
                     .addNetworkInterceptor(loggingInterceptor)
                     .build()


                 val retrofit = Retrofit.Builder()
                     .baseUrl(url)//BASE_URL)
                     .client(okkHttpclient)
   //                  .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
     //                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                     .addConverterFactory(MoshiConverterFactory.create(moshi))
                     //               .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                     .build()
                 return retrofit.create(APIService::class.java)
             }




        }




}


