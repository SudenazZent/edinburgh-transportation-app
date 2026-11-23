package com.example.edinburghtransportation.data.remote

import com.example.edinburghtransportation.data.remote.model.StopsResponse
import com.example.edinburghtransportation.data.remote.model.RoutesResponse
import com.example.edinburghtransportation.data.remote.model.DeparturesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path


interface ApiService {

    @GET("routes")
    suspend fun getRoutes(
        @Query("operator_onestop_id")
        operatorOnestopId: String,

        @Query("route_id")
        routeId: String? = null,

        @Query("include_stops")
        includeStops: Boolean = false,

        @Query("limit")
        limit: Int = 100
    ): RoutesResponse

    @GET("stops")
    suspend fun getStops(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("radius") radius: Int = 5000,
        @Query("limit") limit: Int = 100
    ): StopsResponse

    @GET("stops/{stopKey}/departures")
    suspend fun getDepartures(
        @Path("stopKey") stopKey: String,
        @Query("limit") limit: Int = 10,
        @Query("next") next: Int = 3600
    ): DeparturesResponse
}