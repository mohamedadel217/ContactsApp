package com.example.feature.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StreetUiModel(
    val number: Int? = null,
    val name: String? = null
) : Parcelable