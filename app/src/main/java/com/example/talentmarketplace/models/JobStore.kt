package com.example.talentmarketplace.models

interface JobStore {
    fun findAll(): List<MarketplaceModel>
    fun create(job: MarketplaceModel)
    fun update(job: MarketplaceModel)
    fun delete(job: MarketplaceModel)
}
