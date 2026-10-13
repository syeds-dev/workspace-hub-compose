package com.syedsubahani.workspacehub.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.syedsubahani.workspacehub.R

const val LOADING_TAG = "loading"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceTopBar(title: String, onBack: (() -> Unit)? = null) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_back),
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )
}

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(Modifier.testTag(LOADING_TAG))
    }
}

@Composable
fun ErrorContent(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(message, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        Button(onClick = onRetry) { Text(stringResource(R.string.retry)) }
    }
}

@Composable
fun ContactAvatar(url: String?, name: String, size: Dp, modifier: Modifier = Modifier) {
    val fallback = painterResource(R.drawable.default_dp_icon)
    AsyncImage(
        model = url,
        contentDescription = stringResource(R.string.profile_picture_of, name),
        placeholder = fallback,
        error = fallback,
        fallback = fallback,
        contentScale = ContentScale.Crop,
        modifier = modifier.size(size).clip(CircleShape),
    )
}
