package com.syedsubahani.workspacehub.data.remote

import com.syedsubahani.workspacehub.data.models.RoomsResponse
import retrofit2.Response
import retrofit2.http.GET

interface RoomsApi {
    @GET("/rooms")
    suspend fun getRooms(): Response<RoomsResponse>
}