package com.example.remote.models

import com.squareup.moshi.Json

data class Timezone(

    @Json(name = "offset")
    val offset: String? = null,

    @Json(name = "description")
    val description: String? = null
)