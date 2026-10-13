package com.syedsubahani.workspacehub.ui.roomscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.syedsubahani.workspacehub.R
import com.syedsubahani.workspacehub.common.NetworkStatus
import com.syedsubahani.workspacehub.data.models.RoomsResponse
import com.syedsubahani.workspacehub.data.models.RoomsResponseItem
import com.syedsubahani.workspacehub.ui.components.ErrorContent
import com.syedsubahani.workspacehub.ui.components.LoadingContent
import com.syedsubahani.workspacehub.ui.components.WorkspaceTopBar
import com.syedsubahani.workspacehub.ui.roomscreen.RoomsViewModel.RoomsEvent
import com.syedsubahani.workspacehub.ui.theme.AvailableGrey
import com.syedsubahani.workspacehub.ui.theme.OccupiedRed
import com.syedsubahani.workspacehub.ui.theme.RoomsHeaderCyan
import com.syedsubahani.workspacehub.ui.theme.WorkspaceHubTheme

@Composable
fun RoomsRoute(
    onBack: () -> Unit,
    viewModel: RoomsViewModel = hiltViewModel(),
) {
    val event by viewModel.rooms.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var noInternet by rememberSaveable { mutableStateOf(false) }

    fun loadRooms() {
        if (NetworkStatus.checkForInternet(context)) {
            noInternet = false
            viewModel.getRooms()
        } else {
            noInternet = true
        }
    }

    LaunchedEffect(Unit) {
        if (event is RoomsEvent.Empty) loadRooms()
    }

    RoomsScreen(
        event = if (noInternet) RoomsEvent.Failure(stringResource(R.string.no_internet)) else event,
        onRetry = ::loadRooms,
        onBack = onBack,
    )
}

@Composable
fun RoomsScreen(
    event: RoomsEvent,
    onRetry: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(topBar = { WorkspaceTopBar(stringResource(R.string.rooms_title), onBack) }) { padding ->
        val modifier = Modifier.padding(padding)
        when (event) {
            is RoomsEvent.Loading, is RoomsEvent.Empty -> LoadingContent(modifier)
            is RoomsEvent.Failure -> ErrorContent(event.errorText, onRetry, modifier)
            is RoomsEvent.Success -> RoomsTable(event.resultText, modifier)
        }
    }
}

@Composable
private fun RoomsTable(rooms: List<RoomsResponseItem>, modifier: Modifier = Modifier) {
    val tableDescription = stringResource(R.string.rooms_list_description)
    Column(modifier.fillMaxSize().semantics { contentDescription = tableDescription }) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(RoomsHeaderCyan)
                .padding(vertical = 20.dp)
        ) {
            HeaderCell(stringResource(R.string.max_occupancy))
            HeaderCell(stringResource(R.string.room_status))
        }
        LazyColumn(Modifier.fillMaxSize()) {
            items(rooms, key = { it.id }) { room ->
                RoomRow(room)
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun RowScope.HeaderCell(text: String) {
    Text(
        text = text,
        modifier = Modifier.weight(1f),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF37474F),
    )
}

@Composable
private fun RoomRow(room: RoomsResponseItem) {
    val status = stringResource(if (room.isOccupied) R.string.occupied else R.string.available)
    val rowDescription = stringResource(R.string.room_row_description, room.maxOccupancy, status)
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp)
            .clearAndSetSemantics { contentDescription = rowDescription }
    ) {
        BodyCell(room.maxOccupancy, AvailableGrey)
        BodyCell(status, if (room.isOccupied) OccupiedRed else AvailableGrey)
    }
}

@Composable
private fun RowScope.BodyCell(text: String, color: Color) {
    Text(
        text = text,
        modifier = Modifier.weight(1f),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = color,
    )
}

@Preview(showBackground = true)
@Composable
private fun RoomsScreenPreview() {
    val rooms = RoomsResponse().apply {
        add(RoomsResponseItem("", false, "53539", "1"))
        add(RoomsResponseItem("", true, "71103", "2"))
    }
    WorkspaceHubTheme { RoomsScreen(RoomsEvent.Success(rooms), onRetry = {}, onBack = {}) }
}
