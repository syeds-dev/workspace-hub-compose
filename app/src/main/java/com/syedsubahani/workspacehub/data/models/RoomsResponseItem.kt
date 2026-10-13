package com.syedsubahani.workspacehub.data.models

data class RoomsResponseItem(
    val createdAt: String,
    val isOccupied: Boolean,
    val maxOccupancy: String,
    val id: String
)