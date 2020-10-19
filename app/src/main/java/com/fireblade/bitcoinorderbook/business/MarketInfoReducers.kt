package com.fireblade.bitcoinorderbook.business

import com.fireblade.network.domain.NetworkOrderBook
import com.fireblade.network.domain.NetworkOrderBookEntry
import com.fireblade.network.domain.NetworkTicker
import com.fireblade.network.domain.Status
import javax.inject.Inject

class MarketInfoReducers @Inject constructor() {

    fun reduceTicker(currentState: MarketInfoScreenState, event: Status<NetworkTicker>) =
        when(event) {
            is Status.Success -> {
                when (currentState) {
                    is MarketInfoScreenState.Success -> currentState.copy(
                        ticker = event.result.toModel()
                    )
                    else -> MarketInfoScreenState.Success(
                        ticker = event.result.toModel()
                    )
                }
            }
            is Status.Failure -> MarketInfoScreenState.Error(event.error.message ?: UNKNOWN_ERROR)
        }

    fun reduceOrderBookEntries(currentState: MarketInfoScreenState, event: Status<NetworkOrderBook>) =
        when(event) {
            is Status.Success -> {
                when (currentState) {
                    is MarketInfoScreenState.Success -> currentState.copy(
                        bids = event.result.bids.map(NetworkOrderBookEntry::toBidItem),
                        asks = event.result.asks.map(NetworkOrderBookEntry::toAskItem)
                    )
                    else -> MarketInfoScreenState.Success(
                        ticker = Ticker.empty,
                        bids = event.result.bids.map(NetworkOrderBookEntry::toBidItem),
                        asks = event.result.asks.map(NetworkOrderBookEntry::toAskItem)
                    )
                }
            }
            is Status.Failure -> MarketInfoScreenState.Error(event.error.message ?: UNKNOWN_ERROR)
        }

    companion object {
        const val UNKNOWN_ERROR = "Unknown error occurred"
    }
}

fun NetworkTicker.toModel() =
    Ticker(
        price = price,
        volume = volume,
        low = low,
        high = high
    )

fun NetworkOrderBookEntry.toBidItem() =
    OrderBookItem.Bid(
        price = price,
        amount = amount,
        timestamp = timestamp
    )

fun NetworkOrderBookEntry.toAskItem() =
    OrderBookItem.Ask(
        price = price,
        amount = amount,
        timestamp = timestamp
    )
