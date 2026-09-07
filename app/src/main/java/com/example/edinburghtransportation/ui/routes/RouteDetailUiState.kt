package com.example.edinburghtransportation.ui.routes

import com.example.edinburghtransportation.data.remote.model.RouteDto

data class RouteDetailUiState(
    val isLoading: Boolean = false,
    val route: RouteDto? = null,
    val errorMessage: String? = null
)