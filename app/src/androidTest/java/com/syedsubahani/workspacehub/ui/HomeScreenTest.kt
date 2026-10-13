package com.syedsubahani.workspacehub.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import com.syedsubahani.workspacehub.ui.home.HomeScreen
import com.syedsubahani.workspacehub.ui.theme.WorkspaceHubTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun showsBothModulesAndHandlesClicks() {
        var contactsClicked = false
        var roomsClicked = false
        composeRule.setContent {
            WorkspaceHubTheme {
                HomeScreen(onContactsClick = { contactsClicked = true }, onRoomsClick = { roomsClicked = true })
            }
        }

        composeRule.onNodeWithText("Workspace Hub").assertIsDisplayed()
        composeRule.onNodeWithText("CONTACTS").assertIsDisplayed().performClick()
        composeRule.onNodeWithText("ROOMS").assertIsDisplayed().performClick()

        assertThat(contactsClicked).isTrue()
        assertThat(roomsClicked).isTrue()
    }
}
