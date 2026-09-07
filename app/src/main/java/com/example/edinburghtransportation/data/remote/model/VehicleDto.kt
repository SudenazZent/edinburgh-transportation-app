package com.example.edinburghtransportation.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class VehicleDto(
    val id: String,
    val latitude: Double,
    val longitude: Double
)