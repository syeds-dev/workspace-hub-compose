package com.syedsubahani.workspacehub.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.syedsubahani.workspacehub.ui.navigation.WorkspaceHubNavHost
import com.syedsubahani.workspacehub.ui.theme.WorkspaceHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            WorkspaceHubTheme {
                WorkspaceHubNavHost()
            }
        }
    }
}
