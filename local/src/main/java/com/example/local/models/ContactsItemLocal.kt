package com.example.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactsItemLocal(
    @PrimaryKey(autoGenerate = true)
    val tableId: Int,
    val nat: String? = null,
    val gender: String? = null,
    val phone: String? = null,
    val id: IdLocal? = null,
    val dob: DobLocal? = null,
    val name: NameLocal? = null,
    val registered: RegisteredLocal? = null,
    val location: LocationLocal? = null,
    val login: LoginLocal? = null,
    val cell: String? = null,
    val email: String? = null,
    val picture: PictureLocal? = null,
    val isFavorite: Boolean = false
)
