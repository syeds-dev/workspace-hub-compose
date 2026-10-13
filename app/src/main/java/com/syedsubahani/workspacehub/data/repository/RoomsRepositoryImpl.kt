package com.syedsubahani.workspacehub.data.repository

import com.syedsubahani.workspacehub.data.remote.RoomsApi
import com.syedsubahani.workspacehub.data.models.RoomsResponse
import com.syedsubahani.workspacehub.repository.RoomsRepository
import com.syedsubahani.workspacehub.common.Resource
import java.lang.Exception
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(
    private val api: RoomsApi
) : RoomsRepository {

    override suspend fun getRooms(): Resource<RoomsResponse> {
        return try {
            val response = api.getRooms()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message(), null)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred.", null)
        }
    }
}