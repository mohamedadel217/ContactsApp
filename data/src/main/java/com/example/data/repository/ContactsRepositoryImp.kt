package com.example.data.repository

import com.example.common.Mapper
import com.example.common.PagingModel
import com.example.data.model.ContactsItemDto
import com.example.domain.entity.ContactsItemEntity
import com.example.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContactsRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val contactsMapper: Mapper<ContactsItemDto, ContactsItemEntity>
) : ContactsRepository {

    override suspend fun getContacts(page: Int): Flow<PagingModel<List<ContactsItemEntity>>> {
        return flow {
            try {
                val data = remoteDataSource.getContacts()
                emit(
                    PagingModel(
                        data = contactsMapper.fromList(data.data),
                        total = data.total,
                        currentPage = page
                    )
                )

            } catch (ex: Exception) {
                // If remote request fails
                try {
                    // Get data from LocalDataSource
                    val localData = localDataSource.getContacts()
                    emit(
                        PagingModel(
                            data = contactsMapper.fromList(localData.data),
                            total = localData.total,
                            currentPage = page
                        )
                    )
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    throw ex
                }
            }
        }
    }
}