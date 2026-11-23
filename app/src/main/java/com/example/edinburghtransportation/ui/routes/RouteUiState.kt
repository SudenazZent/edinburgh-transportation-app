package com.example.edinburghtransportation.ui.routes

import com.example.edinburghtransportation.data.remote.model.RouteDto
import com.example.edinburghtransportation.data.remote.model.DepartureDto

data class RouteUiState(
    val isLoading: Boolean = true,
    val routes: List<RouteDto> = emptyList(),
    val departures: List<DepartureDto> = emptyList(),
    val errorMessage: String? = null
)