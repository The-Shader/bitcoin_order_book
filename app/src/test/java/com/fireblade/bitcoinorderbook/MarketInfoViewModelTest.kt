package com.fireblade.bitcoinorderbook

import com.appmattus.kotlinfixture.kotlinFixture
import com.babylon.orbit2.assert
import com.babylon.orbit2.test
import com.fireblade.bitcoinorderbook.business.*
import com.fireblade.network.domain.NetworkOrderBook
import com.fireblade.network.domain.NetworkOrderBookEntry
import com.fireblade.network.domain.NetworkTicker
import com.fireblade.network.domain.Status
import com.fireblade.network.services.MarketService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Test

class MarketInfoViewModelTest {
    private val marketService = mock<MarketService>()
    private val fixture = kotlinFixture()

    // Using the actual state reducer given that it forms a logical unit with the viewmodel
    private val reducers = MarketInfoReducers()

    @Test
    fun `load ticker info from network`() {
        val networkTicker = fixture<NetworkTicker>()
        val ticker = networkTicker.toModel()

        // Mock the network service
        whenever(marketService.getTicker(any())).thenReturn(Flowable.just(Status.Success(networkTicker)))

        val marketInfoViewModel = MarketInfoViewModel(marketService, reducers).test(
            initialState = MarketInfoScreenState.Loading,
            runOnCreate = true
        )

        marketInfoViewModel.loadTickerInfo()

        marketInfoViewModel.assert {
            states(
                { MarketInfoScreenState.Success(ticker = ticker) }
            )
        }
    }

    @Test
    fun `fail to load ticker info from network`() {

        val getTickerException = fixture<Exception>()

        // Mock the network service
        whenever(marketService.getTicker(any())).thenReturn(Flowable.just(Status.Failure(getTickerException)))

        val marketInfoViewModel = MarketInfoViewModel(marketService, reducers).test(
            initialState = MarketInfoScreenState.Loading,
            runOnCreate = true
        )
        marketInfoViewModel.loadTickerInfo()

        marketInfoViewModel.assert {
            states(
                { MarketInfoScreenState.Error(error = getTickerException.message ?: MarketInfoReducers.UNKNOWN_ERROR) }
            )
        }
    }

    @Test
    fun `load orderbook from network`() {

        val networkOrderBook = fixture<NetworkOrderBook>()
        val asks = networkOrderBook.asks.map (NetworkOrderBookEntry::toAskItem)
        val bids = networkOrderBook.bids.map (NetworkOrderBookEntry::toBidItem)

        // Mock the network service
        whenever(marketService.getOrderBook(any())).thenReturn(Flowable.just(Status.Success(networkOrderBook)))

        val marketInfoViewModel = MarketInfoViewModel(marketService, reducers).test(
            initialState = MarketInfoScreenState.Loading,
            runOnCreate = true
        )

        marketInfoViewModel.loadOrderBook()

        marketInfoViewModel.assert {
            states(
                { MarketInfoScreenState.Success(
                    ticker = Ticker.empty,
                    bids = bids,
                    asks = asks
                )}
            )
        }
    }

    @Test
    fun `fail to load wallet balance from repository`() {

        val getOrderBookException = fixture<Exception>()

        // Mock the network service
        whenever(marketService.getOrderBook(any())).thenReturn(Flowable.just(Status.Failure(getOrderBookException)))

        val marketInfoViewModel = MarketInfoViewModel(marketService, reducers).test(
            initialState = MarketInfoScreenState.Loading,
            runOnCreate = true
        )

        marketInfoViewModel.loadOrderBook()

        marketInfoViewModel.assert {
            states(
                { MarketInfoScreenState.Error(error = getOrderBookException.message ?: MarketInfoReducers.UNKNOWN_ERROR) }
            )
        }
    }
}