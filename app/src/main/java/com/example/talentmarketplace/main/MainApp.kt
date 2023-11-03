package com.example.talentmarketplace.main

import android.app.Application
import com.example.talentmarketplace.models.JobMemStore
import com.example.talentmarketplace.models.MarketplaceModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {

    val jobs = JobMemStore()

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        i("onCreate() - application started")

        jobs.create(MarketplaceModel("Test One", "About One"))
        jobs.create(MarketplaceModel("Test Two", "About Two"))
    }
}