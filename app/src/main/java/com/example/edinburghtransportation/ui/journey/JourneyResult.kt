package com.example.edinburghtransportation.ui.journey

import com.example.edinburghtransportation.data.remote.model.RouteDto
import com.example.edinburghtransportation.data.remote.model.StopDto

data class JourneyResult(
    val route: RouteDto,
    val nextDeparture: String?,
    val transferRoute: RouteDto? = null,
    val transferStop: StopDto? = null,
    val transferDeparture: String? = null
)