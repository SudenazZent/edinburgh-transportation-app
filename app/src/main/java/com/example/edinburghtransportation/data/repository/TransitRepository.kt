package com.example.edinburghtransportation.data.repository

import com.example.edinburghtransportation.data.remote.ApiService
import com.example.edinburghtransportation.data.remote.model.RouteDto
import com.example.edinburghtransportation.data.remote.model.StopDto
import javax.inject.Inject

interface TransitRepository {

    suspend fun getRoutes(): List<RouteDto>

    suspend fun getRouteById(routeId: String): RouteDto?

    suspend fun getStops(): List<StopDto>
}

class TransitRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TransitRepository {

    override suspend fun getRoutes(): List<RouteDto> {

        val busRoutes = apiService
            .getRoutes("o-gcvw-lothianbuses")
            .routes

        val tramRoutes = apiService
            .getRoutes("o-gcvw-edinburghtrams")
            .routes

        return busRoutes + tramRoutes
    }
    override suspend fun getStops(): List<StopDto> {
        return apiService.getStops(
            latitude = 55.9533,
            longitude = -3.1883
        ).stops
    }

    override suspend fun getRouteById(routeId: String): RouteDto? {

        return getRoutes().firstOrNull { route ->
            route.routeId == routeId
        }
    }
}