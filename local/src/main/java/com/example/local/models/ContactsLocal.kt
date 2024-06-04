package com.example.local.models

data class ContactsLocal(
    val id: Int,
    val results: List<ContactsItemLocal>? = null,
    val info: InfoLocal? = null
)
