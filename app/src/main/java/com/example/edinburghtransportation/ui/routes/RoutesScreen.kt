package com.example.edinburghtransportation.ui.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.edinburghtransportation.data.remote.model.RouteDto

@Composable
fun RoutesScreen(
    modifier: Modifier = Modifier,
    viewModel: RouteViewModel = hiltViewModel(),
    onRouteClick: (RouteDto) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var searchQuery by remember {
        mutableStateOf("")
    }

    var selectedFilter by remember {
        mutableStateOf("Tümü")
    }

    val filteredRoutes = uiState.routes.filter { route ->

        val matchesFilter =
            selectedFilter == "Tümü" ||
                    routeTypeLabel(route.routeType) == selectedFilter

        val matchesSearch =
            searchQuery.isBlank() ||
                    route.longName?.contains(
                        searchQuery,
                        ignoreCase = true
                    ) == true ||
                    route.shortName?.contains(
                        searchQuery,
                        ignoreCase = true
                    ) == true

        matchesFilter && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text("Hat ara (örn. 12A)")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.padding(vertical = 12.dp)
        ) {

            listOf("Tümü", "Otobüs", "Tramvay").forEach { filter ->

                Button(
                    onClick = {
                        selectedFilter = filter
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (filter == selectedFilter) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondaryContainer
                            }
                    )
                ) {
                    Text(filter)
                }
            }
        }

        when {

            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.errorMessage != null -> {

                Text(
                    text = uiState.errorMessage ?: ""
                )

                Button(
                    onClick = {
                        viewModel.loadRoutes()
                    }
                ) {
                    Text("Tekrar Dene")
                }
            }

            else -> {

                LazyColumn {

                    items(filteredRoutes) { route ->

                        RouteCard(
                            route = route,
                            onClick = { clickedRoute ->
                                onRouteClick(clickedRoute)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun RouteCard(
    route: RouteDto,
    onClick: (RouteDto) -> Unit
) {

    Card(
        onClick = {
            onClick(route)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Row(
            modifier = Modifier.padding(12.dp)
        ) {

            Column(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(routeTypeColor(route.routeType))
            ) {}

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {

                Text(
                    text = route.shortName ?: route.routeId,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = route.longName ?: "",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = routeTypeLabel(route.routeType),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
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


private fun routeTypeColor(routeType: Int): Color =
    when (routeType) {
        0 -> Color.Yellow
        3 -> Color.Blue
        else -> Color.Gray
    }