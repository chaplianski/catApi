package com.example.thecatapi.model

import com.squareup.moshi.Json

data class Cat(

    @Json(name = "id")
    val id: String,

    @Json(name = "url")
    val url: String
)
