package com.example.thecatapi

import com.google.gson.annotations.SerializedName

class Cat (
    @SerializedName("id") val id: String?,
    @SerializedName("url") val imageUrl: String?
  )

