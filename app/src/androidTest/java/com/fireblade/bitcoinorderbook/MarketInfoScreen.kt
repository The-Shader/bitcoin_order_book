package com.fireblade.bitcoinorderbook

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView

class MarketInfoScreen : Screen<MarketInfoScreen>(){
    val tickerPriceText = KTextView { withId(R.id.ticker_price) }
    val tickerPriceChangeText = KTextView { withId(R.id.ticker_price_change) }
    val tickerVolumeText = KTextView { withId(R.id.ticker_volume) }
    val tickerPriceLowText = KTextView { withId(R.id.ticker_price_low) }
    val tickerPriceHighText = KTextView { withId(R.id.ticker_price_high) }
}