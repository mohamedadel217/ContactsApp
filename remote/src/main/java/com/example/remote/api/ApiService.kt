package com.example.remote.api

import com.example.remote.models.ContactsResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/?results=100")
    suspend fun getContacts(): ContactsResponse

}