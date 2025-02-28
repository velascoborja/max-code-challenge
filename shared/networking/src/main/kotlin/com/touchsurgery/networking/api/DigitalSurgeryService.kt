package com.touchsurgery.networking.api

import com.touchsurgery.networking.models.ProcedureDetailsResponse
import com.touchsurgery.networking.models.ProcedurePreviewResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DigitalSurgeryService {

    @GET("/api/v3/procedures")
    suspend fun getProcedures(): List<ProcedurePreviewResponse>

    @GET("/api/v3/procedures/{uuid}")
    suspend fun getProcedureDetails(@Path("uuid") uuid: String): ProcedureDetailsResponse
}
