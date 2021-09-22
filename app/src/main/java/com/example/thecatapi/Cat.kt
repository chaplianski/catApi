package com.example.thecatapi

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class Cat (
    @SerializedName("id") val id: String?,
    @SerializedName("url") val imageUrl: String?
  )

