package com.example.edinburghtransportation.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutesResponse(
    @SerialName("routes") val routes: List<RouteDto> = emptyList()
)

@Serializable
data class RouteDto(
    @SerialName("id") val id: Long,
    @SerialName("route_id") val routeId: String,
    @SerialName("route_short_name") val shortName: String? = null,
    @SerialName("route_long_name") val longName: String? = null,
    @SerialName("route_type") val routeType: Int,
    @SerialName("route_color") val color: String? = null,
    @SerialName("onestop_id") val onestopId: String? = null,
    @SerialName("agency") val agency: AgencyDto? = null
)

@Serializable
data class AgencyDto(
    @SerialName("agency_name") val agencyName: String? = null
)