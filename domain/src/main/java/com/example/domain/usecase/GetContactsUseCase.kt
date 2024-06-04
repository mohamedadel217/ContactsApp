package com.example.domain.usecase

import com.example.base.BaseUseCase
import com.example.common.PagingModel
import com.example.domain.dispatcher.DispatcherProvider
import com.example.domain.entity.ContactsItemEntity
import com.example.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseUseCase<PagingModel<List<ContactsItemEntity>>, Int>() {

    override suspend fun buildRequest(params: Int?): Flow<PagingModel<List<ContactsItemEntity>>> {
        if (params == null) {
            return flow<PagingModel<List<ContactsItemEntity>>> {
                throw Exception("page can't be null")
            }.flowOn(dispatcherProvider.io())
        }
        return contactsRepository.getContacts(params).flowOn(dispatcherProvider.io())
    }

}