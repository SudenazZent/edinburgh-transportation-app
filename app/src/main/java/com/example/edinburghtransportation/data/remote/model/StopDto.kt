package com.example.edinburghtransportation.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StopsResponse(
    @SerialName("stops")
    val stops: List<StopDto> = emptyList()
)

@Serializable
data class StopDto(
    @SerialName("id")
    val id: Long,

    @SerialName("stop_id")
    val stopId: String,

    @SerialName("stop_name")
    val stopName: String? = null,

    @SerialName("onestop_id")
    val onestopId: String? = null,

    @SerialName("geometry")
    val geometry: GeometryDto? = null
)

@Serializable
data class GeometryDto(
    @SerialName("type")
    val type: String,

    @SerialName("coordinates")
    val coordinates: List<Double>
)