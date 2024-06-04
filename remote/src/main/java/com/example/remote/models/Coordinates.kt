package com.example.remote.models

import com.squareup.moshi.Json

data class Coordinates(

    @Json(name = "latitude")
    val latitude: String? = null,

    @Json(name = "longitude")
    val longitude: String? = null
)