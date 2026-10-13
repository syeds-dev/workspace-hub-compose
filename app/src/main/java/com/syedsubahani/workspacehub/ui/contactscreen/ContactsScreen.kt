package com.syedsubahani.workspacehub.ui.contactscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.syedsubahani.workspacehub.R
import com.syedsubahani.workspacehub.common.NetworkStatus
import com.syedsubahani.workspacehub.data.models.ContactsResponse
import com.syedsubahani.workspacehub.data.models.ContactsResponseItem
import com.syedsubahani.workspacehub.ui.components.ContactAvatar
import com.syedsubahani.workspacehub.ui.components.ErrorContent
import com.syedsubahani.workspacehub.ui.components.LoadingContent
import com.syedsubahani.workspacehub.ui.components.WorkspaceTopBar
import com.syedsubahani.workspacehub.ui.contactscreen.ContactsViewModel.ContactsEvent
import com.syedsubahani.workspacehub.ui.theme.WorkspaceHubTheme

@Composable
fun ContactsRoute(
    onContactClick: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: ContactsViewModel = hiltViewModel(),
) {
    val event by viewModel.contacts.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var noInternet by rememberSaveable { mutableStateOf(false) }

    fun loadContacts() {
        if (NetworkStatus.checkForInternet(context)) {
            noInternet = false
            viewModel.getContacts()
        } else {
            noInternet = true
        }
    }

    LaunchedEffect(Unit) {
        if (event is ContactsEvent.Empty) loadContacts()
    }

    ContactsScreen(
        event = if (noInternet) ContactsEvent.Failure(stringResource(R.string.no_internet)) else event,
        onContactClick = onContactClick,
        onRetry = ::loadContacts,
        onBack = onBack,
    )
}

@Composable
fun ContactsScreen(
    event: ContactsEvent,
    onContactClick: (String) -> Unit,
    onRetry: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(topBar = { WorkspaceTopBar(stringResource(R.string.contacts_title), onBack) }) { padding ->
        val modifier = Modifier.padding(padding)
        when (event) {
            is ContactsEvent.Loading, is ContactsEvent.Empty -> LoadingContent(modifier)
            is ContactsEvent.Failure -> ErrorContent(event.errorText, onRetry, modifier)
            is ContactsEvent.Success -> ContactsList(event.resultText, onContactClick, modifier)
        }
    }
}

@Composable
private fun ContactsList(
    contacts: List<ContactsResponseItem>,
    onContactClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listDescription = stringResource(R.string.contact_list_description)
    LazyColumn(modifier.fillMaxSize().semantics { contentDescription = listDescription }) {
        items(contacts, key = { it.id }) { contact ->
            ContactRow(contact, onClick = { onContactClick(contact.id) })
            HorizontalDivider()
        }
    }
}

@Composable
private fun ContactRow(contact: ContactsResponseItem, onClick: () -> Unit) {
    val fullName = contact.fullName()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        ContactAvatar(url = contact.avatar, name = fullName, size = 80.dp)
        Spacer(Modifier.width(24.dp))
        Text(text = fullName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
    }
}

internal fun ContactsResponseItem.fullName() = "$firstName $lastName"

internal fun previewContacts() = ContactsResponse().apply {
    add(ContactsResponseItem("", "", "maggie@example.com", "Blue", "Maggie", "1", "Engineer", "Brekke"))
    add(ContactsResponseItem("", "", "armando@example.com", "Green", "Armando", "2", "Designer", "Weber"))
}

@Preview(showBackground = true)
@Composable
private fun ContactsScreenPreview() {
    WorkspaceHubTheme {
        ContactsScreen(ContactsEvent.Success(previewContacts()), onContactClick = {}, onRetry = {}, onBack = {})
    }
}
