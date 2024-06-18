package com.example.domain.usecase

import com.example.base.BaseUseCase
import com.example.domain.dispatcher.DispatcherProvider
import com.example.domain.entity.ContactsItemEntity
import com.example.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveFavoriteContactUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun execute(params: ContactsItemEntity?): Int {
        return withContext(dispatcherProvider.io()) {
            contactsRepository.saveFavoriteContact(params)
        }
    }
}