package com.example.edinburghtransportation.data.remote

import com.example.edinburghtransportation.data.remote.model.StopsResponse
import com.example.edinburghtransportation.data.remote.model.RoutesResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("routes")
    suspend fun getRoutes(
        @Query("operator_onestop_id") operatorOnestopId: String,
        @Query("limit") limit: Int = 100
    ): RoutesResponse

    @GET("stops")
    suspend fun getStops(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("radius") radius: Int = 5000,
        @Query("limit") limit: Int = 100
    ): StopsResponse
}