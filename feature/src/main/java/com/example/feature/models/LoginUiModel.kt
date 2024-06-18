package com.example.feature.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginUiModel(
    val sha1: String? = null,
    val password: String? = null,
    val salt: String? = null,
    val sha256: String? = null,
    val uuid: String,
    val username: String? = null,
    val md5: String? = null
) : Parcelable