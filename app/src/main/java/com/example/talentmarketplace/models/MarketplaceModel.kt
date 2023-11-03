package com.example.talentmarketplace.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarketplaceModel(var title: String = "",
                            var description: String = ""): Parcelable
