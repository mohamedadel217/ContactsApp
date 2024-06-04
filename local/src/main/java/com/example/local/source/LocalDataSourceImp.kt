package com.example.local.source

import com.example.common.Mapper
import com.example.common.PagingModel
import com.example.data.model.ContactsItemDto
import com.example.data.repository.LocalDataSource
import com.example.local.database.ContactsDao
import com.example.local.models.ContactsItemLocal
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    private val contactsDao: ContactsDao,
    private val contactsLocalDataMapper: Mapper<ContactsItemLocal, ContactsItemDto>
) : LocalDataSource {

    override suspend fun getContacts(): PagingModel<List<ContactsItemDto>> {
        val data = contactsDao.getAllContacts()
        return PagingModel(
            data = contactsLocalDataMapper.fromList(data),
            total = 1, // get only first page from local database if there is no internet
            currentPage = 0
        )
    }

}