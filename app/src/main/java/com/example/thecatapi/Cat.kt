package com.example.thecatapi

import com.google.gson.annotations.SerializedName

class Cat (
    @SerializedName("id") val id: String?,
    @SerializedName("image") val imageUrl: String?
  )

