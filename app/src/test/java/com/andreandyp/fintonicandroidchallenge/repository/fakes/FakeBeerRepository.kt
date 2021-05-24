package com.andreandyp.fintonicandroidchallenge.repository.fakes

import com.andreandyp.fintonicandroidchallenge.domain.Beer
import com.andreandyp.fintonicandroidchallenge.repository.BeerRepository
import com.andreandyp.fintonicandroidchallenge.repository.Result

class FakeBeerRepository(private val beers: List<Beer>) : BeerRepository {
    var throwError = false
    override suspend fun getBeersFromRemote(page: Int, beersPerPage: Int): List<Beer> = emptyList()
    override suspend fun getBeersFromLocal(): List<Beer> = emptyList()

    override suspend fun getBeers(page: Int, forceUpdate: Boolean): Result<List<Beer>> {
        return if(throwError) {
            Result.Error(Exception())
        } else {
            Result.Success(beers)
        }
    }

    override suspend fun saveBeersToLocal(beers: List<Beer>) = Unit
}