package com.andreandyp.fintonicandroidchallenge.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreandyp.fintonicandroidchallenge.domain.Beer

@Entity(tableName = "beers")
data class BeerEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "tagline") val tagline: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "first_brewed") val firstBrewed: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "food_pairing") val foodPairing: String
)

fun Beer.asEntity() = BeerEntity(
    id = this.id,
    name = this.name,
    tagline = this.tagline,
    description = this.description,
    firstBrewed = this.firstBrewed,
    imageUrl = this.imageUrl,
    foodPairing = this.foodPairing
)

fun BeerEntity.asDomain() = Beer(
    id = this.id,
    name = this.name,
    tagline = this.tagline,
    description = this.description,
    firstBrewed = this.firstBrewed,
    imageUrl = this.imageUrl,
    foodPairing = this.foodPairing,
)