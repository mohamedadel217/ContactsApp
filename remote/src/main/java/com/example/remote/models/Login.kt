package com.example.remote.models

import com.squareup.moshi.Json

data class Login(

    @Json(name = "sha1")
    val sha1: String? = null,

    @Json(name = "password")
    val password: String? = null,

    @Json(name = "salt")
    val salt: String? = null,

    @Json(name = "sha256")
    val sha256: String? = null,

    @Json(name = "uuid")
    val uuid: String,

    @Json(name = "username")
    val username: String? = null,

    @Json(name = "md5")
    val md5: String? = null
)