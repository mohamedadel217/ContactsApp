package com.example.data.repository

import com.example.common.PagingModel
import com.example.data.model.ContactsItemDto

interface LocalDataSource {

    suspend fun getContacts(): PagingModel<List<ContactsItemDto>>
    suspend fun saveContacts(data: List<ContactsItemDto>): List<Long>
    suspend fun getFavoriteContacts(): PagingModel<List<ContactsItemDto>>
    suspend fun addFavoriteContacts(contact: ContactsItemDto): Int
    suspend fun clearContactsCashed(): Int

}