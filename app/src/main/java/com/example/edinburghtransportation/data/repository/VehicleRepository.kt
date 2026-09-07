package com.example.edinburghtransportation.data.repository

import com.example.edinburghtransportation.data.remote.VehicleApiService
import com.example.edinburghtransportation.data.remote.model.VehicleDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepository @Inject constructor(
    private val vehicleApiService: VehicleApiService
) {

    suspend fun getVehicles(): List<VehicleDto> {
        return vehicleApiService.getVehicles()
    }
}