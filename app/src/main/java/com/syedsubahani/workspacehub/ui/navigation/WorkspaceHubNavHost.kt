package com.syedsubahani.workspacehub.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.syedsubahani.workspacehub.ui.contactscreen.ContactDetailsRoute
import com.syedsubahani.workspacehub.ui.contactscreen.ContactsRoute
import com.syedsubahani.workspacehub.ui.contactscreen.ContactsViewModel
import com.syedsubahani.workspacehub.ui.home.HomeScreen
import com.syedsubahani.workspacehub.ui.roomscreen.RoomsRoute

object Routes {
    const val HOME = "home"
    const val CONTACTS = "contacts"
    const val CONTACT_ID = "contactId"
    const val CONTACT_DETAILS = "contactDetails/{$CONTACT_ID}"
    const val ROOMS = "rooms"

    fun contactDetails(contactId: String) = "contactDetails/$contactId"
}

@Composable
fun WorkspaceHubNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.HOME) {
            HomeScreen(
                onContactsClick = { navController.navigate(Routes.CONTACTS) },
                onRoomsClick = { navController.navigate(Routes.ROOMS) },
            )
        }

        composable(Routes.CONTACTS) {
            ContactsRoute(
                onContactClick = { id -> navController.navigate(Routes.contactDetails(id)) },
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = Routes.CONTACT_DETAILS,
            arguments = listOf(navArgument(Routes.CONTACT_ID) { type = NavType.StringType }),
        ) { entry ->
            val contactsEntry = remember(entry) { navController.getBackStackEntry(Routes.CONTACTS) }
            val viewModel: ContactsViewModel = hiltViewModel(contactsEntry)

            ContactDetailsRoute(
                contactId = entry.arguments?.getString(Routes.CONTACT_ID).orEmpty(),
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.ROOMS) {
            RoomsRoute(onBack = { navController.popBackStack() })
        }
    }
}
