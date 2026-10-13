package com.syedsubahani.workspacehub.ui.roomscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syedsubahani.workspacehub.data.models.RoomsResponse
import com.syedsubahani.workspacehub.repository.RoomsRepository
import com.syedsubahani.workspacehub.common.DispatcherProvider
import com.syedsubahani.workspacehub.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val repository: RoomsRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class RoomsEvent {
        class Success(val resultText: RoomsResponse) : RoomsEvent()
        class Failure(val errorText: String) : RoomsEvent()
        object Loading : RoomsEvent()
        object Empty : RoomsEvent()
    }

    private val _rooms = MutableStateFlow<RoomsEvent>(RoomsEvent.Empty)
    val rooms: StateFlow<RoomsEvent> = _rooms
    fun getRooms() {
        viewModelScope.launch(dispatchers.io) {
            _rooms.value = RoomsEvent.Loading
            when (val roomsResponse = repository.getRooms()) {
                is Resource.Error -> _rooms.value =
                    RoomsEvent.Failure(roomsResponse.message!!)
                is Resource.Success -> {
                    var rooms = roomsResponse.data!!

                    if (rooms == null) {
                        _rooms.value = RoomsEvent.Failure("Unexpected error")
                    } else {
                        _rooms.value = RoomsEvent.Success(
                            rooms
                        )
                    }
                }
            }
        }
    }
}