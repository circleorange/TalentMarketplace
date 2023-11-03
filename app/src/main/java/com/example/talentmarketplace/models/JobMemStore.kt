package com.example.talentmarketplace.models

import timber.log.Timber.i

var lastID = 0L

internal fun getID(): Long { return lastID++ }

class JobMemStore: JobStore {
    val jobs = ArrayList<MarketplaceModel>()

    override fun findAll(): List<MarketplaceModel> {
        return jobs }

    override fun create(job: MarketplaceModel) {
        job.id = getID()
        jobs.add(job)
        logAll() }

    override fun update(job: MarketplaceModel) {
        var foundJob: MarketplaceModel? = jobs.find { updatedJob -> updatedJob.id == job.id }
        if (foundJob != null) {
            foundJob.title = job.title
            foundJob.description = job.description
            logAll() } }

    fun logAll() {
        jobs.forEach{ i("$it") } }
}
