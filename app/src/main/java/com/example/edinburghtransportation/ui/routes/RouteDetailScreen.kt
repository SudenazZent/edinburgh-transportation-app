package com.example.edinburghtransportation.ui.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.statusBarsPadding
import com.example.edinburghtransportation.R


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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {

        // Geri butonu ve başlık
        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
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
                    text = stringResource(R.string.route_detail),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        when {

            // Hat bilgileri yükleniyor
            uiState.isLoading -> {

                item {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = stringResource(
                                R.string.route_info_loading
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Hata
            uiState.errorMessage != null -> {

                item {

                    Text(
                        text = uiState.errorMessage ?: "",
                        modifier = Modifier.padding(20.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Hat bilgileri geldi
            uiState.route != null -> {

                uiState.route?.let { route ->

                    // Hattın duraklarını al
                    val stops = route.routeStops.mapNotNull {
                        it.stop
                    }

                    val displayStops = when {
                        stops.size <= 10 -> {
                            stops
                        }

                        else -> {
                            val lastIndex = stops.lastIndex

                            List(10) { index ->
                                stops[(index * lastIndex) / 9]
                            }.distinct()
                        }
                    }

                    val firstStop = stops.firstOrNull()
                    val lastStop = stops.lastOrNull()

                    // Hat bilgileri
                    item {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {

                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {

                                // Hat numarası
                                Text(
                                    text = route.shortName
                                        ?: route.routeId,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .background(
                                            color = routeTypeColor(
                                                route.routeType
                                            ),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .padding(
                                            horizontal = 14.dp,
                                            vertical = 7.dp
                                        )
                                )

                                Spacer(
                                    modifier = Modifier.height(14.dp)
                                )

                                // Başlangıç
                                if (firstStop != null) {

                                    Text(
                                        text = firstStop.stopName
                                            ?: stringResource(
                                                R.string.start
                                            ),
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Text(
                                    text = "→",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                // Bitiş
                                if (lastStop != null) {

                                    Text(
                                        text = lastStop.stopName
                                            ?: stringResource(
                                                R.string.finish
                                            ),
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Text(
                                    text = routeTypeLabel(
                                        route.routeType
                                    ),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Yaklaşan seferler başlığı
                    item {

                        Text(
                            text = stringResource(
                                R.string.upcoming_departures
                            ),
                            modifier = Modifier.padding(
                                start = 20.dp,
                                top = 8.dp
                            ),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                    }

                    // Sefer durumları
                    if (uiState.isDeparturesLoading) {

                        item {

                            Text(
                                text = stringResource(
                                    R.string.departures_loading
                                ),
                                modifier = Modifier.padding(
                                    horizontal = 20.dp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                    } else if (uiState.departures.isEmpty()) {

                        item {

                            Text(
                                text = stringResource(
                                    R.string.no_upcoming_departures
                                ),
                                modifier = Modifier.padding(
                                    horizontal = 20.dp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                    } else {

                        // İlk 3 yaklaşan sefer
                        item {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 20.dp
                                    ),
                                horizontalArrangement =
                                    Arrangement.spacedBy(10.dp)
                            ) {

                                uiState.departures
                                    .take(3)
                                    .forEach { departure ->

                                        DepartureChip(
                                            time = formatDepartureTime(
                                                departure.departureTime
                                            )
                                        )
                                    }
                            }
                        }
                    }

                    // Duraklar başlığı
                    item {

                        Text(
                            text = stringResource(
                                R.string.stops
                            ),
                            modifier = Modifier.padding(
                                start = 20.dp,
                                top = 12.dp
                            ),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Timeline
                    items(displayStops) { stop ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 20.dp
                                ),
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Column(
                                horizontalAlignment =
                                    Alignment.CenterHorizontally
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        )
                                )

                                if (stop != displayStops.last()) {

                                    Spacer(
                                        modifier = Modifier
                                            .height(32.dp)
                                            .width(2.dp)
                                            .background(
                                                MaterialTheme.colorScheme.primary
                                            )
                                    )
                                }
                            }

                            Spacer(
                                modifier = Modifier.width(12.dp)
                            )

                            Text(
                                text = stop.stopName
                                    ?: stringResource(
                                        R.string.unknown_stop
                                    ),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    item {
                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DepartureChip(
    time: String
) {

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF3CD)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Text(
            text = time,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 10.dp
            ),
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8A6500)
        )
    }
}

private fun formatDepartureTime(
    departureTime: String?
): String {

    if (departureTime.isNullOrBlank()) {
        return "--:--"
    }

    return try {

        departureTime
            .substringAfter("T")
            .substring(0, 5)

    } catch (_: Exception) {
        departureTime
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
        0 -> Color(0xFFE0A800)
        3 -> Color(0xFF0B4598)
        else -> Color(0xFF7A8799)
    }