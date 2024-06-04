package com.example.remote.models

import com.squareup.moshi.Json

data class ContactsResponse(

    @Json(name = "results")
    val results: List<ContactsItem>? = null,

    @Json(name = "info")
    val info: Info? = null
)



