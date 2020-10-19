package com.fireblade.bitcoinorderbook.business

import java.math.BigDecimal
import java.math.RoundingMode

data class Ticker (
    val price: Double,
    val volume: Double,
    val low: Double,
    val high: Double
) {
    val changeInPercent: BigDecimal
        get() = if (price != 0.0 ) {
            (((high - low) / 2.0) / price * 100.0).toBigDecimal()
                .setScale(4, RoundingMode.FLOOR)
        }
        else {
            BigDecimal.ZERO
        }

    companion object {
        val empty = Ticker(0.0, 0.0, 0.0, 0.0)
    }
}
