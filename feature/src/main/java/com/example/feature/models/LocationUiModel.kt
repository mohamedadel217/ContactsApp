package com.example.feature.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationUiModel(
    val country: String? = null,
    val city: String? = null,
    val street: StreetUiModel? = null,
    val postcode: String? = null,
    val state: String? = null,
): Parcelable