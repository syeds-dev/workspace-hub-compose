package com.syedsubahani.workspacehub.ui.contactscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.syedsubahani.workspacehub.R
import com.syedsubahani.workspacehub.data.models.ContactsResponseItem
import com.syedsubahani.workspacehub.ui.components.ContactAvatar
import com.syedsubahani.workspacehub.ui.components.ErrorContent
import com.syedsubahani.workspacehub.ui.components.LoadingContent
import com.syedsubahani.workspacehub.ui.components.WorkspaceTopBar
import com.syedsubahani.workspacehub.ui.contactscreen.ContactsViewModel.ContactsEvent
import com.syedsubahani.workspacehub.ui.theme.ContactHeaderBlue
import com.syedsubahani.workspacehub.ui.theme.WorkspaceHubTheme

@Composable
fun ContactDetailsRoute(
    contactId: String,
    viewModel: ContactsViewModel,
    onBack: () -> Unit,
) {
    val event by viewModel.contacts.collectAsStateWithLifecycle()
    val contact = (event as? ContactsEvent.Success)?.resultText?.firstOrNull { it.id == contactId }

    ContactDetailsScreen(contact = contact, isLoading = event is ContactsEvent.Loading, onBack = onBack)
}

@Composable
fun ContactDetailsScreen(
    contact: ContactsResponseItem?,
    isLoading: Boolean,
    onBack: () -> Unit,
) {
    Scaffold(topBar = { WorkspaceTopBar(stringResource(R.string.contact_details_title), onBack) }) { padding ->
        val modifier = Modifier.padding(padding)
        when {
            contact != null -> ContactDetailsContent(contact, modifier)
            isLoading -> LoadingContent(modifier)
            else -> ErrorContent(stringResource(R.string.contact_not_found), onRetry = onBack, modifier)
        }
    }
}

@Composable
private fun ContactDetailsContent(contact: ContactsResponseItem, modifier: Modifier = Modifier) {
    val screenDescription = stringResource(R.string.contact_details_description)
    val headerDescription = stringResource(R.string.header_description)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = screenDescription },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(ContactHeaderBlue)
                .padding(vertical = 32.dp)
                .semantics { contentDescription = headerDescription },
        ) {
            ContactAvatar(url = contact.avatar, name = contact.fullName(), size = 140.dp)
            Text(
                text = contact.fullName(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.semantics { heading() },
            )
        }
        HorizontalDivider(thickness = 4.dp, color = MaterialTheme.colorScheme.primary)

        Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            DetailRow(stringResource(R.string.email_side_heading), contact.email)
            DetailRow(stringResource(R.string.job_title_side_heading), contact.jobtitle)
            DetailRow(stringResource(R.string.fav_color_side_heading), contact.favouriteColor)
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row {
        Text(label, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(value, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactDetailsPreview() {
    WorkspaceHubTheme {
        ContactDetailsScreen(contact = previewContacts().first(), isLoading = false, onBack = {})
    }
}
