package com.syedsubahani.workspacehub.ui.contactscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syedsubahani.workspacehub.data.models.ContactsResponse
import com.syedsubahani.workspacehub.repository.ContactsRepository
import com.syedsubahani.workspacehub.common.DispatcherProvider
import com.syedsubahani.workspacehub.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repository: ContactsRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class ContactsEvent {
        class Success(val resultText: ContactsResponse) : ContactsEvent()
        class Failure(val errorText: String) : ContactsEvent()
        object Loading : ContactsEvent()
        object Empty : ContactsEvent()
    }

    private val _contacts = MutableStateFlow<ContactsEvent>(ContactsEvent.Empty)
    val contacts: StateFlow<ContactsEvent> = _contacts
    fun getContacts() {
        viewModelScope.launch(dispatchers.io) {
            _contacts.value = ContactsEvent.Loading
            when (val contactsResponse = repository.getContacts()) {
                is Resource.Error -> _contacts.value =
                    ContactsEvent.Failure(contactsResponse.message!!)
                is Resource.Success -> {
                    var contacts = contactsResponse.data!!

                    if (contacts == null) {
                        _contacts.value = ContactsEvent.Failure("Unexpected error")
                    } else {
                        _contacts.value = ContactsEvent.Success(
                            contacts
                        )
                    }
                }
            }
        }
    }
}