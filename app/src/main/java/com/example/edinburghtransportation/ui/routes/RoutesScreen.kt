package com.example.edinburghtransportation.ui.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.edinburghtransportation.R
import com.example.edinburghtransportation.data.remote.model.RouteDto

@Composable
fun RoutesScreen(
    modifier: Modifier = Modifier,
    viewModel: RouteViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onRouteClick: (RouteDto) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var searchQuery by remember {
        mutableStateOf("")
    }

    // Burada Türkçe metin tutmuyoruz.
    // Çünkü dil değiştiğinde filtre değerinin değişmesini istemiyoruz.
    var selectedFilter by remember {
        mutableStateOf("all")
    }

    val filteredRoutes = uiState.routes.filter { route ->

        val matchesFilter =
            when (selectedFilter) {
                "all" -> true
                "bus" -> route.routeType == 3
                "tram" -> route.routeType == 0
                else -> true
            }

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
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
    ) {

        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = stringResource(R.string.routes),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.route_search_hint),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            listOf(
                "all" to R.string.all,
                "bus" to R.string.bus,
                "tram" to R.string.tram
            ).forEach { (filterKey, filterText) ->

                Button(
                    onClick = {
                        selectedFilter = filterKey
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (filterKey == selectedFilter) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.surface
                            },
                        contentColor =
                            if (filterKey == selectedFilter) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Text(
                        text = stringResource(filterText),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        when {

            uiState.isLoading -> {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            uiState.errorMessage != null -> {

                Text(
                    text = uiState.errorMessage ?: "",
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(
                    onClick = {
                        viewModel.loadRoutes()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.try_again)
                    )
                }
            }

            else -> {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(
                        items = filteredRoutes,
                        key = { it.routeId }
                    ) { route ->

                        RouteCard(
                            route = route,
                            onClick = {
                                onRouteClick(route)
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
    onClick: () -> Unit
) {

    val stops = route.routeStops.mapNotNull {
        it.stop
    }

    val firstStop = stops.firstOrNull()
    val lastStop = stops.lastOrNull()

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = route.shortName ?: route.routeId,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        color = routeTypeColor(route.routeType),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 6.dp
                    )
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            if (firstStop != null && lastStop != null) {

                Text(
                    text = "${
                        firstStop.stopName
                            ?: stringResource(R.string.start)
                    } → ${
                        lastStop.stopName
                            ?: stringResource(R.string.finish)
                    }",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )
            }

            Text(
                text = routeTypeLabel(route.routeType),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun routeTypeLabel(
    routeType: Int
): String =
    when (routeType) {
        0 -> stringResource(R.string.tram)
        3 -> stringResource(R.string.bus)
        else -> stringResource(R.string.other)
    }

private fun routeTypeColor(
    routeType: Int
): Color =
    when (routeType) {
        0 -> Color(0xFFE0A800)
        3 -> Color(0xFF0B4598)
        else -> Color(0xFF7A8799)
    }