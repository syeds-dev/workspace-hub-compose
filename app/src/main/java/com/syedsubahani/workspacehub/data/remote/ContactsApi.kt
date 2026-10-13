package com.syedsubahani.workspacehub.data.remote

import com.syedsubahani.workspacehub.data.models.ContactsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ContactsApi {
    @GET("/people")
    suspend fun getContacts(): Response<ContactsResponse>
}