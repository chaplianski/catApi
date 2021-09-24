package com.example.thecatapi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Cat (
    @Json (name = "id") var id: String?,
    @Json (name = "url") var url: String?

    )



