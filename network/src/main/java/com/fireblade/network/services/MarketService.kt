package com.fireblade.network.services

import com.fireblade.network.domain.NetworkOrderBook
import com.fireblade.network.domain.NetworkTicker
import com.fireblade.network.domain.Status
import io.reactivex.Flowable
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MarketService (
    private val marketApiService: IMarketApiService
) {
    fun getTicker(symbol: String) : Flowable<Status<NetworkTicker>> =
        marketApiService.getTicker(symbol).toFlowable()
            .debounce(5, TimeUnit.SECONDS)
            .map<Status<NetworkTicker>> {
                Status.Success(it)
            }
            .onErrorReturn {
                Status.Failure(Exception(it.message))
            }

    fun getOrderBook(symbol: String) : Flowable<Status<NetworkOrderBook>> =
        marketApiService.getOrderBook(symbol).toFlowable()
            .debounce(5, TimeUnit.SECONDS)
            .map<Status<NetworkOrderBook>> {
                Status.Success(it)
            }
            .onErrorReturn {
                Status.Failure(Exception(it.message))
            }
}