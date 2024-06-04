package com.example.remote.source

import com.example.common.Mapper
import com.example.common.PagingModel
import com.example.data.model.ContactsItemDto
import com.example.data.repository.RemoteDataSource
import com.example.remote.api.ApiService
import com.example.remote.models.ContactsItem
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(
    private val apiService: ApiService,
    private val contactsMapper: Mapper<ContactsItem, ContactsItemDto>
) : RemoteDataSource {

    override suspend fun getContacts(): PagingModel<List<ContactsItemDto>> {
        val data = apiService.getContacts()
        return PagingModel(
            data = contactsMapper.fromList(data.results),
            total = data.info?.results ?: 0,
            currentPage = data.info?.page ?: 1
        )
    }

}