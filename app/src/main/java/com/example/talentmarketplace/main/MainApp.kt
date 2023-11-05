package com.example.talentmarketplace.main

import android.app.Application
import com.example.talentmarketplace.models.JobJSONStore
import com.example.talentmarketplace.models.JobMemStore
import com.example.talentmarketplace.models.JobStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {

    lateinit var jobs: JobStore

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        i("onCreate() - application started")

        // jobs = JobMemStore()
        jobs = JobJSONStore(this)
    }
}
