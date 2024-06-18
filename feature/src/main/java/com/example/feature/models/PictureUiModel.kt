package com.example.feature.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureUiModel(
    val thumbnail: String? = null,
    val large: String? = null,
    val medium: String? = null
) : Parcelable