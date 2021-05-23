package com.andreandyp.fintonicandroidchallenge.datasources

import com.andreandyp.fintonicandroidchallenge.domain.Beer

interface LocalBeersDataSource {
    suspend fun getBeers(): List<Beer>
    suspend fun saveBeers(beers: List<Beer>)
}