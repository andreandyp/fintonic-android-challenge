package com.andreandyp.fintonicandroidchallenge.repository.fakes

import com.andreandyp.fintonicandroidchallenge.datasources.RemoteBeersDataSource
import com.andreandyp.fintonicandroidchallenge.domain.Beer

class FakeRemoteBeersDataSource(private val beers: List<Beer>) : RemoteBeersDataSource {
    var throwError = false
    override suspend fun getBeers(page: Int, beersPerPage: Int): List<Beer> {
        if (throwError) {
            throw Exception()
        } else {
            return beers
        }
    }
}
