package com.example.data.model

data class ContactsDto(
    val results: List<ContactsItemDto>? = null,
    val info: InfoDto? = null
)