package com.andreandyp.fintonicandroidchallenge.repository

import com.andreandyp.fintonicandroidchallenge.domain.Beer

interface BeerRepository {
    suspend fun getBeersFromRemote(page: Int, beersPerPage: Int): List<Beer>
    suspend fun getBeersFromLocal(): List<Beer>
    suspend fun getBeers(page: Int, forceUpdate: Boolean): Result<List<Beer>>
    suspend fun saveBeersToLocal(beers: List<Beer>)
}