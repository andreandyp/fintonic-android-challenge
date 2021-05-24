package com.andreandyp.fintonicandroidchallenge.repository.fakes

import com.andreandyp.fintonicandroidchallenge.datasources.LocalBeersDataSource
import com.andreandyp.fintonicandroidchallenge.domain.Beer

class FakeLocalBeersDataSource(private val defaultBeers: MutableList<Beer>) : LocalBeersDataSource {
    var throwError = false
    override suspend fun getBeers(): List<Beer> {
        if(throwError) {
            throw Exception()
        } else {
            return defaultBeers
        }
    }

    override suspend fun saveBeers(beers: List<Beer>) {
        defaultBeers.addAll(beers)
    }
}