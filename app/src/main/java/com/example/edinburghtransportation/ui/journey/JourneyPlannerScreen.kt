package com.example.edinburghtransportation.ui.journey

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.edinburghtransportation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyPlannerScreen(
    viewModel: JourneyPlannerViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {

    val stops by viewModel.stops
        .collectAsStateWithLifecycle()

    val journeyResults by viewModel.journeyResults
        .collectAsStateWithLifecycle()

    val isLoading by viewModel.isLoading
        .collectAsStateWithLifecycle()

    val errorMessage by viewModel.errorMessage
        .collectAsStateWithLifecycle()

    val selectedFromStop by viewModel.selectedFromStop
        .collectAsStateWithLifecycle()

    val selectedToStop by viewModel.selectedToStop
        .collectAsStateWithLifecycle()

    var showStopSelector by remember {
        mutableStateOf(false)
    }

    var selectingFrom by remember {
        mutableStateOf(true)
    }

    val sheetState =
        rememberModalBottomSheetState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 30.dp)
        ) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                contentAlignment = Alignment.CenterStart
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription =
                            stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = stringResource(R.string.journey_planner),
                    modifier = Modifier.padding(
                        start = 42.dp
                    ),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Text(
                text = stringResource(R.string.from),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            JourneyField(
                text =
                    selectedFromStop?.stopName
                        ?: stringResource(R.string.starting_point),

                onClick = {
                    selectingFrom = true
                    showStopSelector = true
                }
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = stringResource(R.string.to),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            JourneyField(
                text =
                    selectedToStop?.stopName
                        ?: stringResource(R.string.destination_point),

                onClick = {
                    selectingFrom = false
                    showStopSelector = true
                }
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Button(
                onClick = {
                    viewModel.findJourney()
                },

                enabled = !isLoading,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),

                shape = RoundedCornerShape(10.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {

                Text(
                    text =
                        if (isLoading) {
                            stringResource(R.string.searching)
                        } else {
                            stringResource(R.string.find_journey)
                        },

                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = stringResource(R.string.journey_results),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (isLoading) {

                Text(
                    text = stringResource(R.string.journey_searching),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            } else if (errorMessage != null) {

                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            } else if (journeyResults.isEmpty()) {

                Text(
                    text = stringResource(R.string.no_journey_yet),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    items(journeyResults) { result ->

                        JourneyResultCard(
                            result = result
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )
                    }
                }
            }
        }

        if (showStopSelector) {

            ModalBottomSheet(
                onDismissRequest = {
                    showStopSelector = false
                },

                sheetState = sheetState
            ) {

                Text(
                    text =
                        if (selectingFrom) {
                            stringResource(
                                R.string.select_starting_point
                            )
                        } else {
                            stringResource(
                                R.string.select_destination_point
                            )
                        },

                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    ),

                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp
                        ),

                    contentPadding =
                        PaddingValues(
                            bottom = 32.dp
                        )
                ) {

                    items(stops) { stop ->

                        Text(
                            text =
                                stop.stopName
                                    ?: stringResource(
                                        R.string.unknown_stop
                                    ),

                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                    if (selectingFrom) {

                                        viewModel
                                            .selectFromStop(
                                                stop
                                            )

                                    } else {

                                        viewModel
                                            .selectToStop(
                                                stop
                                            )
                                    }

                                    showStopSelector = false
                                }

                                .padding(
                                    vertical = 18.dp,
                                    horizontal = 8.dp
                                ),

                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun JourneyField(
    text: String,
    onClick: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(10.dp),

        color = MaterialTheme.colorScheme.surfaceVariant,

        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 14.dp
                ),

            contentAlignment =
                Alignment.CenterStart
        ) {

            Text(
                text = text,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Icon(
                imageVector =
                    Icons.Default.KeyboardArrowDown,

                contentDescription =
                    stringResource(
                        R.string.select_options
                    ),

                modifier = Modifier.align(
                    Alignment.CenterEnd
                ),

                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun JourneyResultCard(
    result: JourneyResult
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(14.dp),

        color = MaterialTheme.colorScheme.surface
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text =
                    if (result.route.routeType == 3) {
                        stringResource(
                            R.string.route_format,
                            result.route.shortName.orEmpty()
                        )
                    } else {
                        stringResource(
                            R.string.tram_format,
                            result.route.shortName.orEmpty()
                        )
                    },

                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = stringResource(R.string.next_departure),
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text =
                    result.nextDeparture
                        ?: stringResource(
                            R.string.departure_info_not_found
                        ),

                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            if (result.transferRoute == null) {

                Text(
                    text = stringResource(R.string.no_transfer),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

            } else {

                Text(
                    text =
                        stringResource(
                            R.string.transfer,
                            result.transferStop
                                ?.stopName
                                ?: "-"
                        ),

                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text =
                        if (
                            result.transferRoute
                                .routeType == 3
                        ) {
                            stringResource(
                                R.string.route_format,
                                result.transferRoute
                                    .shortName
                                    .orEmpty()
                            )
                        } else {
                            stringResource(
                                R.string.tram_format,
                                result.transferRoute
                                    .shortName
                                    .orEmpty()
                            )
                        },

                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = stringResource(
                        R.string.next_departure
                    ),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text =
                        result.transferDeparture
                            ?: stringResource(
                                R.string.departure_info_not_found
                            ),

                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}