package com.example.feature.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactsItemUiModel(
    val id: IdUiModel? = null,
    val phone: String? = null,
    val name: NameUiModel? = null,
    val location: LocationUiModel? = null,
    val cell: String? = null,
    val email: String? = null,
    val picture: PictureUiModel? = null,
    val login: LoginUiModel,
    var isFavorite: Boolean = false
) : Parcelable