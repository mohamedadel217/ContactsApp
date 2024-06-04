package com.example.contactsapp.di

import com.example.common.Mapper
import com.example.data.mapper.ContactsDataDomainMapper
import com.example.data.model.ContactsItemDto
import com.example.domain.entity.ContactsItemEntity
import com.example.local.mapper.ContactsLocalDataMapper
import com.example.local.models.ContactsItemLocal
import com.example.remote.mapper.ContactsNetworkDataMapper
import com.example.remote.models.ContactsItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module that holds Mappers
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    //region Data Mappers
    @Binds
    abstract fun bindsNewDataDomainMapper(mapper: ContactsDataDomainMapper): Mapper<ContactsItemDto, ContactsItemEntity>
    //endregion

    //region Remote Mappers
    @Binds
    abstract fun bindsNewNetworkDataMapper(mapper: ContactsNetworkDataMapper): Mapper<ContactsItem, ContactsItemDto>
    //endregion

    //region local Mappers
    @Binds
    abstract fun bindsNewLocalDataMapper(mapper: ContactsLocalDataMapper): Mapper<ContactsItemLocal, ContactsItemDto>
    //endregion

    //region Presentation Mappers
//    @Binds
//    abstract fun bindsNewDomainUiMapper(mapper: NewDomainUiMapper): Mapper<NewEntity, NewUiModel>
    //endregion
}