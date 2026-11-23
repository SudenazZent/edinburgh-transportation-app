package com.example.edinburghtransportation.ui.routes

import com.example.edinburghtransportation.data.remote.model.DepartureDto
import com.example.edinburghtransportation.data.remote.model.RouteDto

data class RouteDetailUiState(
    val isLoading: Boolean = false,
    val isDeparturesLoading: Boolean = false,
    val route: RouteDto? = null,
    val departures: List<DepartureDto> = emptyList(),
    val errorMessage: String? = null
)