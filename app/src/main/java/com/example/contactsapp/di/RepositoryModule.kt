package com.example.contactsapp.di

import com.example.data.repository.ContactsRepositoryImp
import com.example.data.repository.LocalDataSource
import com.example.data.repository.RemoteDataSource
import com.example.domain.repository.ContactsRepository
import com.example.local.source.LocalDataSourceImp
import com.example.remote.source.RemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImp: RemoteDataSourceImp): RemoteDataSource

    @Binds
    abstract fun provideLocalDataSource(localDataSource: LocalDataSourceImp): LocalDataSource

    @Binds
    @ViewModelScoped
    abstract fun provideContactsRepository(repository: ContactsRepositoryImp): ContactsRepository

}