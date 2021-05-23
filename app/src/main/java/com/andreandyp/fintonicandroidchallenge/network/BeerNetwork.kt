package com.andreandyp.fintonicandroidchallenge.network

import com.andreandyp.fintonicandroidchallenge.database.entities.BeerEntity
import com.andreandyp.fintonicandroidchallenge.domain.Beer
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BeerNetwork(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,

    @Json(name = "first_brewed")
    val firstBrewed: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "food_pairing")
    val foodPairing: List<String>
)

fun BeerNetwork.asDomain() = Beer(
    id = this.id,
    name = this.name,
    tagline = this.tagline,
    description = this.description,
    firstBrewed = this.firstBrewed,
    imageUrl = this.imageUrl,
    foodPairing = this.foodPairing.joinToString(",").replace(",", "\n")
)
