package com.fireblade.bitcoinorderbook

import androidx.test.rule.ActivityTestRule
import com.fireblade.bitcoinorderbook.ui.HomeActivity
import org.junit.Rule
import org.junit.Test

class MarketInfoScreenWalkthroughTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(HomeActivity::class.java)

    private val marketInfoScreen = MarketInfoScreen()

    @Test
    fun load_market_info_screen() {

        marketInfoScreen {
            tickerPriceText.isDisplayed()
            tickerPriceChangeText.isDisplayed()
            tickerVolumeText.isDisplayed()
            tickerPriceLowText.isDisplayed()
            tickerPriceHighText.isDisplayed()
        }
    }
}