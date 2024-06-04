package com.example.local.database

import androidx.room.*
import com.example.local.models.ContactsItemLocal

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContacts(news: ContactsItemLocal): Long

    @Query("select * from contacts")
    fun getAllContacts(): List<ContactsItemLocal>

    @Query("delete from contacts")
    fun clearContactsCash(): Int

}