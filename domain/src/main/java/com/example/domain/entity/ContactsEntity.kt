package com.example.domain.entity

data class ContactsEntity(
    val results: List<ContactsItemEntity>? = null,
    val info: InfoEntity? = null
)