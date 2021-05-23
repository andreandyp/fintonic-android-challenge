package com.andreandyp.fintonicandroidchallenge.repository

import com.andreandyp.fintonicandroidchallenge.datasources.LocalBeersDataSource
import com.andreandyp.fintonicandroidchallenge.datasources.RemoteBeersDataSource
import com.andreandyp.fintonicandroidchallenge.domain.Beer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BeerRepositoryImpl(
    private val localBeersDataSource: LocalBeersDataSource,
    private val remoteBeersDataSource: RemoteBeersDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BeerRepository {
    override suspend fun getBeersFromRemote(page: Int, beersPerPage: Int): List<Beer> =
        withContext(dispatcher) {
            val beers = remoteBeersDataSource.getBeers(page, beersPerPage)
            saveBeersToLocal(beers)
            return@withContext beers
        }

    override suspend fun getBeersFromLocal(): List<Beer> = withContext(dispatcher) {
        return@withContext localBeersDataSource.getBeers()
    }

    override suspend fun getBeers(page: Int, forceUpdate: Boolean): Result<List<Beer>> {
        return try {
            if (forceUpdate) {
                Result.Success(getBeersFromRemote(page, BEERS_PER_PAGE))
            } else {
                Result.Success(getBeersFromLocal())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveBeersToLocal(beers: List<Beer>) = withContext(dispatcher) {
        localBeersDataSource.saveBeers(beers)
    }

    companion object {
        private const val BEERS_PER_PAGE = 20
    }
}