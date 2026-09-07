package com.example.edinburghtransportation.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RouteDetailScreen(
    routeId: String,
    onBackClick: () -> Unit,
    viewModel: RouteDetailViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(routeId) {
        viewModel.loadRoute(routeId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Geri"
            )
        }

        when {

            uiState.isLoading -> {
                Text("Yükleniyor...")
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage ?: ""
                )
            }

            uiState.route != null -> {

                val route = uiState.route

                if (route != null) {

                    Text(
                        text = route.shortName ?: ""
                    )

                    Text(
                        text = route.longName ?: ""
                    )

                    Text(
                        text = routeTypeLabel(route.routeType)
                    )
                }
            }
        }
    }
}

private fun routeTypeLabel(routeType: Int): String =
    when (routeType) {
        0 -> "Tramvay"
        3 -> "Otobüs"
        else -> "Diğer"
    }