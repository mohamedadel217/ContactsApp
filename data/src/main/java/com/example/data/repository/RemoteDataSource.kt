package com.example.data.repository

import com.example.common.PagingModel
import com.example.data.model.ContactsItemDto

interface RemoteDataSource {

    suspend fun getContacts(): PagingModel<List<ContactsItemDto>>

}