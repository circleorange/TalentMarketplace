package com.example.talentmarketplace.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarketplaceModel(var id: Long = 0,
                            var title: String = "",
                            var description: String = ""): Parcelable
