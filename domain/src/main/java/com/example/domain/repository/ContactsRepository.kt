package com.example.domain.repository

import com.example.common.PagingModel
import com.example.domain.entity.ContactsItemEntity
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {

    suspend fun getContacts(page: Int): Flow<PagingModel<List<ContactsItemEntity>>>
    suspend fun getFavoriteContacts(page: Int): Flow<PagingModel<List<ContactsItemEntity>>>
    suspend fun saveFavoriteContact(contact: ContactsItemEntity?): Int

}