package com.fireblade.network.domain

data class NetworkOrderBook(
    val bids: List<NetworkOrderBookEntry>,
    val asks: List<NetworkOrderBookEntry>
)