package com.syedsubahani.workspacehub.data.repository

import com.syedsubahani.workspacehub.common.Resource
import com.syedsubahani.workspacehub.common.Status
import com.syedsubahani.workspacehub.data.models.ContactsResponse
import com.syedsubahani.workspacehub.data.models.ContactsResponseItem
import com.syedsubahani.workspacehub.repository.ContactsRepositoryTest

class FakeContactsRepositoryImpl : ContactsRepositoryTest{
    private var shouldReturnNetworkError = "Network is Good"
    private var contact1 = ContactsResponseItem(
        "avatar1",
        "createdAt1",
        "email1",
        "favouriteColor1",
        "firstName1",
        "id1",
        "jobtitle1",
        "lastName1"
    )

    private var contact2 = ContactsResponseItem(
        "avatar2",
        "createdAt2",
        "email2",
        "favouriteColor2",
        "firstName2",
        "id2",
        "jobtitle2",
        "lastName2"
    )

    fun setShouldReturnNetworkError():String{
        shouldReturnNetworkError = "Network Error"
        return shouldReturnNetworkError
    }

    override suspend fun getContacts(status:Status): Resource<ContactsResponse> {
        return when (status) {
            Status.SUCCESS -> {
                var contactResponse = ContactsResponse()
                contactResponse.add(contact1)
                contactResponse.add(contact2)

                Resource.Success(contactResponse)
            }
            else -> {
                Resource.Error("An Error occurred", null)
            }
        }
    }
}
