package com.fireblade.bitcoinorderbook.business

import androidx.lifecycle.ViewModel
import com.babylon.orbit2.ContainerHost
import com.babylon.orbit2.reduce
import com.babylon.orbit2.rxjava2.transformRx2Observable
import com.babylon.orbit2.viewmodel.container
import com.fireblade.network.services.MarketService
import javax.inject.Inject

class MarketInfoViewModel @Inject constructor(
    private val marketService: MarketService,
    private val reducers: MarketInfoReducers
) : ContainerHost<MarketInfoScreenState, Nothing>, ViewModel() {

    override val container = container<MarketInfoScreenState, Nothing>(MarketInfoScreenState.Loading)

    private val symbol = "btcusd"

    fun refresh() = orbit {

        reduce {
            MarketInfoScreenState.Loading
        }
    }

    fun loadTickerInfo() = orbit {
        transformRx2Observable {
            marketService.getTicker(symbol).toObservable()
        }
            .reduce {
                reducers.reduceTicker(state, event)
            }
    }

    fun loadOrderBook() = orbit {
        transformRx2Observable {
            marketService.getOrderBook(symbol).toObservable()
        }
            .reduce {
                reducers.reduceOrderBookEntries(state, event)
            }
    }
}