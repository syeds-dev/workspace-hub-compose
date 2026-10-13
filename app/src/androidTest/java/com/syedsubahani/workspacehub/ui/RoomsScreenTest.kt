package com.syedsubahani.workspacehub.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.syedsubahani.workspacehub.data.models.RoomsResponse
import com.syedsubahani.workspacehub.data.models.RoomsResponseItem
import com.syedsubahani.workspacehub.ui.roomscreen.RoomsScreen
import com.syedsubahani.workspacehub.ui.roomscreen.RoomsViewModel.RoomsEvent
import com.syedsubahani.workspacehub.ui.theme.WorkspaceHubTheme
import org.junit.Rule
import org.junit.Test

class RoomsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun eachRoomRowIsReadAsOneSentence() {
        val rooms = RoomsResponse().apply {
            add(RoomsResponseItem("", false, "12", "1"))
            add(RoomsResponseItem("", true, "8", "2"))
        }
        composeRule.setContent {
            WorkspaceHubTheme { RoomsScreen(RoomsEvent.Success(rooms), onRetry = {}, onBack = {}) }
        }

        composeRule.onNodeWithContentDescription("Maximum occupancy 12, Available").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Maximum occupancy 8, Occupied").assertIsDisplayed()
    }
}
