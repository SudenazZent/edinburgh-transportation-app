package com.example.edinburghtransportation.ui.routes

import com.example.edinburghtransportation.data.remote.model.RouteDto

data class RouteUiState(
    val isLoading: Boolean = true,
    val routes: List<RouteDto> = emptyList(),
    val errorMessage: String? = null
)