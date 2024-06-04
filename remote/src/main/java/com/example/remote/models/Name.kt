package com.example.remote.models

import com.squareup.moshi.Json

data class Name(

    @Json(name = "last")
    val last: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "first")
    val first: String
)