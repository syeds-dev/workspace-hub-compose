package com.syedsubahani.workspacehub.repository

import com.syedsubahani.workspacehub.common.Resource
import com.syedsubahani.workspacehub.common.Status
import com.syedsubahani.workspacehub.data.models.ContactsResponse

interface ContactsRepositoryTest {
        suspend fun getContacts(status: Status): Resource<ContactsResponse>
}