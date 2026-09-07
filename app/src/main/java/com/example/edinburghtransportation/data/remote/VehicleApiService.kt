package com.example.edinburghtransportation.data.remote

import com.example.edinburghtransportation.data.remote.model.VehicleDto
import retrofit2.http.GET

interface VehicleApiService {

    @GET("vehicles")
    suspend fun getVehicles(): List<VehicleDto>
}