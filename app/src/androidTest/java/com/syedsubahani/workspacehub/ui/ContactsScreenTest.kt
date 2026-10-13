package com.syedsubahani.workspacehub.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import com.syedsubahani.workspacehub.data.models.ContactsResponse
import com.syedsubahani.workspacehub.data.models.ContactsResponseItem
import com.syedsubahani.workspacehub.ui.components.LOADING_TAG
import com.syedsubahani.workspacehub.ui.contactscreen.ContactDetailsScreen
import com.syedsubahani.workspacehub.ui.contactscreen.ContactsScreen
import com.syedsubahani.workspacehub.ui.contactscreen.ContactsViewModel.ContactsEvent
import com.syedsubahani.workspacehub.ui.theme.WorkspaceHubTheme
import org.junit.Rule
import org.junit.Test

class ContactsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val maggie = ContactsResponseItem("", "", "maggie@example.com", "Blue", "Maggie", "1", "Engineer", "Brekke")
    private val armando = ContactsResponseItem("", "", "armando@example.com", "Green", "Armando", "2", "Designer", "Weber")
    private val contacts = ContactsResponse().apply { add(maggie); add(armando) }

    @Test
    fun loadingEventShowsProgress() {
        composeRule.setContent {
            WorkspaceHubTheme { ContactsScreen(ContactsEvent.Loading, onContactClick = {}, onRetry = {}, onBack = {}) }
        }
        composeRule.onNodeWithTag(LOADING_TAG).assertIsDisplayed()
    }

    @Test
    fun successEventShowsNamesAndReportsClickedId() {
        var clickedId: String? = null
        composeRule.setContent {
            WorkspaceHubTheme {
                ContactsScreen(ContactsEvent.Success(contacts), onContactClick = { clickedId = it }, onRetry = {}, onBack = {})
            }
        }

        composeRule.onNodeWithText("Maggie Brekke").assertIsDisplayed()
        composeRule.onNodeWithText("Armando Weber").performClick()

        assertThat(clickedId).isEqualTo("2")
    }

    @Test
    fun failureEventShowsMessageAndRetry() {
        var retried = false
        composeRule.setContent {
            WorkspaceHubTheme {
                ContactsScreen(ContactsEvent.Failure("No Internet Connection!"), onContactClick = {}, onRetry = { retried = true }, onBack = {})
            }
        }

        composeRule.onNodeWithText("No Internet Connection!").assertIsDisplayed()
        composeRule.onNodeWithText("Retry").performClick()

        assertThat(retried).isTrue()
    }

    @Test
    fun detailsScreenShowsAllFields() {
        composeRule.setContent {
            WorkspaceHubTheme { ContactDetailsScreen(contact = maggie, isLoading = false, onBack = {}) }
        }

        composeRule.onNodeWithText("Maggie Brekke").assertIsDisplayed()
        composeRule.onNodeWithText("maggie@example.com").assertIsDisplayed()
        composeRule.onNodeWithText("Engineer").assertIsDisplayed()
        composeRule.onNodeWithText("Blue").assertIsDisplayed()
    }
}
