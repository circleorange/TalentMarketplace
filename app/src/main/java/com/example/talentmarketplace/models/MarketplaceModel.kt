package com.example.talentmarketplace.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class MarketplaceModel(
    var id: Long = 0,
    var companyName: String = "",
    var title: String = "",
    var description: String = "",
    var startDate: Date? = null,
    var minSalary: Float? = null,
    var maxSalary: Float? = null,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
): Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
): Parcelable
