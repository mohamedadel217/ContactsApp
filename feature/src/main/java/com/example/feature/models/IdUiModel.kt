package com.example.feature.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IdUiModel(
    val name: String? = null,
    val value: String? = null
) : Parcelable