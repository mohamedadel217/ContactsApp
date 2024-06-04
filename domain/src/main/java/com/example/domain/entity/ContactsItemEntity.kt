package com.example.domain.entity

data class ContactsItemEntity(
    val nat: String? = null,
    val gender: String? = null,
    val phone: String? = null,
    val dob: DobEntity? = null,
    val name: NameEntity? = null,
    val registered: RegisteredEntity? = null,
    val location: LocationEntity? = null,
    val id: IdEntity? = null,
    val login: LoginEntity? = null,
    val cell: String? = null,
    val email: String? = null,
    val picture: PictureEntity? = null,
    val isFavorite: Boolean = false
)