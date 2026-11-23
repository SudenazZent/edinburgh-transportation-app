package com.example.edinburghtransportation.data.repository

import com.example.edinburghtransportation.data.remote.ApiService
import com.example.edinburghtransportation.data.remote.model.RouteDto
import com.example.edinburghtransportation.data.remote.model.StopDto
import javax.inject.Inject
import com.example.edinburghtransportation.data.remote.model.DepartureDto

interface TransitRepository {

    suspend fun getRoutes(): List<RouteDto>

    suspend fun getRouteById(routeId: String): RouteDto?

    suspend fun getStops(): List<StopDto>

    suspend fun getUpcomingDepartures(
        routeId: String,
        stopId: Long
    ): List<DepartureDto>
}

class TransitRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TransitRepository {

    override suspend fun getRoutes(): List<RouteDto> {

        val busRoutes = apiService
            .getRoutes(
                operatorOnestopId = "o-gcvw-lothianbuses",
                includeStops = true
            )
            .routes

        val tramRoutes = apiService
            .getRoutes(
                operatorOnestopId = "o-gcvw-edinburghtrams",
                includeStops = true
            )
            .routes

        return busRoutes + tramRoutes
    }
    override suspend fun getStops(): List<StopDto> {
        val routes = getRoutes()

        return routes
            .flatMap { route ->
                route.routeStops.mapNotNull { it.stop }
            }
            .distinctBy { it.id }
    }

    override suspend fun getRouteById(routeId: String): RouteDto? {

        val busRoute = apiService
            .getRoutes(
                operatorOnestopId = "o-gcvw-lothianbuses",
                routeId = routeId,
                includeStops = true
            )
            .routes
            .firstOrNull()

        if (busRoute != null) {
            return busRoute
        }

        return apiService
            .getRoutes(
                operatorOnestopId = "o-gcvw-edinburghtrams",
                routeId = routeId,
                includeStops = true
            )
            .routes
            .firstOrNull()
    }
    override suspend fun getUpcomingDepartures(
        routeId: String,
        stopId: Long
    ): List<DepartureDto> {

        val response = apiService.getDepartures(
            stopKey = stopId.toString(),
            limit = 10,
            next = 10800
        )

        return response.stops
            .flatMap { it.departures }
            .filter { departure ->
                departure.trip?.route?.routeId == routeId
            }
    }
}