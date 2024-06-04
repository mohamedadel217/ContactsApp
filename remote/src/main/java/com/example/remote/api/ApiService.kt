package com.example.remote.api

import com.example.remote.models.ContactsResponse
import retrofit2.http.GET

interface ApiService {
    @GET
    suspend fun getContacts(): ContactsResponse

}