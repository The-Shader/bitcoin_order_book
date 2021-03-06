package com.fireblade.network.domain

sealed class Status<T> {

    data class Success<T>(val result: T) : Status<T>()

    data class Failure<T>(val error: Exception) : Status<T>()
}