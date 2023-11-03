package com.example.talentmarketplace.models

import timber.log.Timber.i


class JobMemStore: JobStore {
    val jobs = ArrayList<MarketplaceModel>()

    override fun findAll(): List<MarketplaceModel> {
        return jobs }

    override fun create(job: MarketplaceModel) {
        jobs.add(job)
        logAll() }

    fun logAll() {
        jobs.forEach{ i("$it") } }
}
