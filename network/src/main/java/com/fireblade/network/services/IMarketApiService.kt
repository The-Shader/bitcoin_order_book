package com.fireblade.network.services

import com.fireblade.network.domain.NetworkOrderBook
import com.fireblade.network.domain.NetworkTicker
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface IMarketApiService {
    @GET("/v1/pubticker/{symbol}")
    fun getTicker(@Path("symbol") symbol: String) : Single<NetworkTicker>

    @GET("/v1/book/{symbol}")
    fun getOrderBook(@Path("symbol") symbol: String) : Single<NetworkOrderBook>

    companion object Factory {

        fun create(marketApiSdk: MarketApiSdk): IMarketApiService =
            marketApiSdk.getClient().create(IMarketApiService::class.java)
    }
}