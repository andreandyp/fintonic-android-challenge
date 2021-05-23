package com.andreandyp.fintonicandroidchallenge.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val firstBrewed: String,
    val imageUrl: String,
    val foodPairing: String
) : Parcelable