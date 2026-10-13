package com.syedsubahani.workspacehub.common

sealed class Resource<T>(val status: Status, val data: T?, val message: String?) {
     class Success<T>(data: T) : Resource<T>(Status.SUCCESS, data, null)
     class Error<T>(message: String, data:T?) :Resource<T>(Status.ERROR, data, message)
}

enum class Status {
     SUCCESS,
     ERROR
}