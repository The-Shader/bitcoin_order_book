package com.fireblade.network.di

import android.content.Context
import com.fireblade.network.services.HttpClientBuilder
import com.fireblade.network.services.IMarketApiService
import com.fireblade.network.services.MarketApiSdk
import com.fireblade.network.services.MarketService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideMarketService(marketApiService: IMarketApiService) : MarketService =
        MarketService(marketApiService)

    @Provides
    fun provideMarketApiService(marketApiSdk: MarketApiSdk) : IMarketApiService =
        IMarketApiService.create(marketApiSdk)

    @Provides
    fun provideMarketApiSdk(httpClient: OkHttpClient, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory, gsonConverterFactory: GsonConverterFactory) =
        MarketApiSdk(
            httpClient,
            rxJava2CallAdapterFactory,
            gsonConverterFactory
        )

    @Provides
    fun provideOkHttpClient(context: Context) =
        HttpClientBuilder(
            context
        ).build()

    @Provides
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()
}