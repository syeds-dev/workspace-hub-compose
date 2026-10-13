package com.syedsubahani.workspacehub.data.repository

import com.syedsubahani.workspacehub.repository.ContactsRepository
import com.syedsubahani.workspacehub.data.remote.ContactsApi
import com.syedsubahani.workspacehub.data.models.ContactsResponse
import com.syedsubahani.workspacehub.common.Resource
import java.lang.Exception
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val api: ContactsApi
) : ContactsRepository {

    override suspend fun getContacts(): Resource<ContactsResponse> {
        return try {
            val response = api.getContacts()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                response.body()?.let {
                    return@let Resource.Success(it)
                }
                return Resource.Success(result)
            } else {
                return Resource.Error(response.message(), null)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred.", null)
        }
    }
}