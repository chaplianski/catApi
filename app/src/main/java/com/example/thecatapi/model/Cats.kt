package com.example.thecatapi.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
//data class Cats(
 //   @Json(name = "cats") val cats: List<Cat>)


data class Cats(

    @Json(name="cats")
    val cats: List<Cat>
)

data class Cat(

//    @Json(name="categories")
//    val categories: List<Any?>,

    @Json(name="id")
    val id: String ,

    @Json(name="url")
    val url: String ,

//    @Json(name="breeds")
//    val breeds: List<Any?>?
)
