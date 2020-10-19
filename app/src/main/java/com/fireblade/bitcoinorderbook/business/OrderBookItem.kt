package com.fireblade.bitcoinorderbook.business

sealed class OrderBookItem {
    data class Bid(
        val price: Double,
        val amount: Double,
        val timestamp: Long
    ) : OrderBookItem()

    data class Ask(
        val price: Double,
        val amount: Double,
        val timestamp: Long
    ) : OrderBookItem()
}
