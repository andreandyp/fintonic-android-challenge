package com.andreandyp.fintonicandroidchallenge.datasources

import com.andreandyp.fintonicandroidchallenge.domain.Beer

interface RemoteBeersDataSource {
    suspend fun getBeers(page: Int, beersPerPage: Int): List<Beer>
}