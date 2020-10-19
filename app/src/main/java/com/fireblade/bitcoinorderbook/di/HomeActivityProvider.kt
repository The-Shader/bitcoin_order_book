package com.fireblade.bitcoinorderbook.di

import com.fireblade.bitcoinorderbook.ui.HomeActivity
import com.fireblade.bitcoinorderbook.ui.MarketInfoFragment
import com.fireblade.network.di.NetworkModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface HomeActivityProvider {
    @ContributesAndroidInjector(modules = [NetworkModule::class])
    fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [NetworkModule::class])
    abstract fun bindMarketInfoFragment(): MarketInfoFragment
}