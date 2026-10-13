package com.syedsubahani.workspacehub.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syedsubahani.workspacehub.R
import com.syedsubahani.workspacehub.ui.components.WorkspaceTopBar
import com.syedsubahani.workspacehub.ui.theme.WorkspaceHubTheme

@Composable
fun HomeScreen(
    onContactsClick: () -> Unit,
    onRoomsClick: () -> Unit,
) {
    Scaffold(topBar = { WorkspaceTopBar(stringResource(R.string.app_name)) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HomeTile(
                icon = R.drawable.contacts_icon,
                label = stringResource(R.string.contacts_label),
                description = stringResource(R.string.contacts_button_label_description),
                onClick = onContactsClick,
            )
            HomeTile(
                icon = R.drawable.office_room_icon,
                label = stringResource(R.string.rooms_label),
                description = stringResource(R.string.rooms_button_label_description),
                onClick = onRoomsClick,
            )
        }
    }
}

@Composable
private fun HomeTile(
    @DrawableRes icon: Int,
    label: String,
    description: String,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .clip(RoundedCornerShape(16.dp))
            .clickable(role = Role.Button, onClick = onClick)
            .semantics { contentDescription = description }
            .padding(16.dp),
    ) {
        Image(painterResource(icon), contentDescription = null, modifier = Modifier.size(150.dp))
        Spacer(Modifier.height(8.dp))
        Text(
            text = label,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    WorkspaceHubTheme { HomeScreen(onContactsClick = {}, onRoomsClick = {}) }
}
