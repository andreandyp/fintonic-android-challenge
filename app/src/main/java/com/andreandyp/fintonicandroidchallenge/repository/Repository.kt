package com.andreandyp.fintonicandroidchallenge.repository

import com.andreandyp.fintonicandroidchallenge.database.entities.BeerEntity
import com.andreandyp.fintonicandroidchallenge.network.BeerNetwork

interface Repository {
    suspend fun getBeersFromInternet(page: String, perPage: String): List<BeerNetwork>
    suspend fun getBeersFromDatabase(): List<BeerEntity>
    suspend fun getBeers(page: String, forceUpdate: Boolean): Result<Any>
    suspend fun saveBeersFromInternet(beers: List<BeerNetwork>)
}