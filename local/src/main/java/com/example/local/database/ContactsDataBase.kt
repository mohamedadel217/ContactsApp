package com.example.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.local.converters.CustomTypesConverter
import com.example.local.models.ContactsItemLocal

@Database(
    entities = [ContactsItemLocal::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CustomTypesConverter::class)
abstract class ContactsDataBase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
}
