package com.example.remote.models

import com.squareup.moshi.Json

data class Picture(

    @Json(name = "thumbnail")
    val thumbnail: String,

    @Json(name = "large")
    val large: String,

    @Json(name = "medium")
    val medium: String
)