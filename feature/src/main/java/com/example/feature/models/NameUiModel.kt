package com.example.feature.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NameUiModel(
    val last: String? = null,
    val title: String? = null,
    val first: String? = null
) : Parcelable