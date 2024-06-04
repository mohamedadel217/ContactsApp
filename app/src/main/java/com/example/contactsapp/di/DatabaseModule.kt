package com.example.contactsapp.di

import dagger.Provides
import android.content.Context
import androidx.room.Room
import com.example.local.database.ContactsDataBase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideContactsDatabase(@ApplicationContext application: Context): ContactsDataBase {
        return Room
            .databaseBuilder(application, ContactsDataBase::class.java, "contacts_database")
            .build()
    }

    @Provides
    @Singleton
    fun provideContactsDao(contactsDataBase: ContactsDataBase) =
        contactsDataBase.contactsDao()
}