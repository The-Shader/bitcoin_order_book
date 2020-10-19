package com.fireblade.network.domain

data class NetworkOrderBookEntry(
    val price: Double,
    val amount: Double,
    val timestamp: Long
)