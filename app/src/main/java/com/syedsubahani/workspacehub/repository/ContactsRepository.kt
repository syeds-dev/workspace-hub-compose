package com.syedsubahani.workspacehub.repository

import com.syedsubahani.workspacehub.data.models.ContactsResponse
import com.syedsubahani.workspacehub.common.Resource

interface ContactsRepository {
    suspend fun getContacts(): Resource<ContactsResponse>
}