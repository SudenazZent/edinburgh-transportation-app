package com.example.edinburghtransportation.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeparturesResponse(
    @SerialName("stops")
    val stops: List<DepartureStopDto> = emptyList()
)

@Serializable
data class DepartureStopDto(
    @SerialName("departures")
    val departures: List<DepartureDto> = emptyList()
)

@Serializable
data class DepartureDto(
    @SerialName("departure_time")
    val departureTime: String? = null,

    @SerialName("trip")
    val trip: DepartureTripDto? = null
)

@Serializable
data class DepartureTripDto(
    @SerialName("route")
    val route: DepartureRouteDto? = null
)

@Serializable
data class DepartureRouteDto(
    @SerialName("route_id")
    val routeId: String? = null
)