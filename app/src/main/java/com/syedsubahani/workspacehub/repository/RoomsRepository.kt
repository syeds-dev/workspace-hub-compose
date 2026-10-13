package com.syedsubahani.workspacehub.repository

import com.syedsubahani.workspacehub.data.models.RoomsResponse
import com.syedsubahani.workspacehub.common.Resource

interface RoomsRepository {
    suspend fun getRooms(): Resource<RoomsResponse>
}