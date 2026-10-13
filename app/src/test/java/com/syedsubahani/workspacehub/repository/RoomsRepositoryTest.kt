package com.syedsubahani.workspacehub.repository

import com.syedsubahani.workspacehub.common.Resource
import com.syedsubahani.workspacehub.common.Status
import com.syedsubahani.workspacehub.data.models.RoomsResponse

interface RoomsRepositoryTest {
        suspend fun getRooms(status: Status): Resource<RoomsResponse>
}