package com.example.local.database

import androidx.room.*
import com.example.local.models.ContactsItemLocal

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContacts(contact: List<ContactsItemLocal>): List<Long>

    @Query("select * from contacts")
    fun getAllContacts(): List<ContactsItemLocal>

    @Query("select * from contacts where isFavorite = 1")
    fun getAllFavoriteContacts(): List<ContactsItemLocal>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(contact: ContactsItemLocal): Int

    @Query("delete from contacts")
    fun clearContactsCash(): Int

}