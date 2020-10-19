package com.fireblade.bitcoinorderbook.business

sealed class MarketInfoScreenState {
    object Loading: MarketInfoScreenState()

    data class Success(
        val ticker: Ticker,
        val bids: List<OrderBookItem> = listOf(),
        val asks: List<OrderBookItem> = listOf()
    ) : MarketInfoScreenState()

    data class Error(val error: String) : MarketInfoScreenState()
}