package com.syedsubahani.workspacehub.data.repository

import com.syedsubahani.workspacehub.common.Resource
import com.syedsubahani.workspacehub.common.Status
import com.syedsubahani.workspacehub.data.models.RoomsResponse
import com.syedsubahani.workspacehub.data.models.RoomsResponseItem
import com.syedsubahani.workspacehub.repository.RoomsRepositoryTest

class FakeRoomsRepositoryImpl : RoomsRepositoryTest {
    private var shouldReturnNetworkError = "Network is Good"
    private var room1 = RoomsResponseItem(
        "createdAt1",
        true,
        "12345",
        "1"
    )

    private var room2 = RoomsResponseItem(
        "createdAt2",
        false,
        "23467",
        "2"
    )

    fun setShouldReturnNetworkError():String{
        shouldReturnNetworkError = "Network Error"
        return shouldReturnNetworkError
    }

    override suspend fun getRooms(status:Status): Resource<RoomsResponse> {
        return when (status) {
            Status.SUCCESS -> {
                var roomsResponse = RoomsResponse()
                roomsResponse.add(room1)
                roomsResponse.add(room2)

                Resource.Success(roomsResponse)
            }
            else -> {
                Resource.Error("An Error occurred", null)
            }
        }
    }
}
