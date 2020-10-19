package com.fireblade.network.domain

import com.google.gson.annotations.SerializedName

data class NetworkTicker (
    @SerializedName("last_price")
    val price: Double,
    val volume: Double,
    val low: Double,
    val high: Double
)

//"mid":"244.755",
//"bid":"244.75",
//"ask":"244.76",
//"last_price":"244.82",
//"low":"244.2",
//"high":"248.19",
//"volume":"7842.11542563",
//"timestamp":"1444253422.348340958"