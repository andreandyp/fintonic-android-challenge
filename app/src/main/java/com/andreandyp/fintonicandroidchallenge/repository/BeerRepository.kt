package com.andreandyp.fintonicandroidchallenge.repository

import com.andreandyp.fintonicandroidchallenge.database.BeerDatabase
import com.andreandyp.fintonicandroidchallenge.database.BeersDao
import com.andreandyp.fintonicandroidchallenge.database.entities.BeerEntity
import com.andreandyp.fintonicandroidchallenge.database.entities.asDomain
import com.andreandyp.fintonicandroidchallenge.database.entities.asEntity
import com.andreandyp.fintonicandroidchallenge.domain.Beer
import com.andreandyp.fintonicandroidchallenge.network.API
import com.andreandyp.fintonicandroidchallenge.network.BeerNetwork
import com.andreandyp.fintonicandroidchallenge.network.asDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BeerRepository(
    database: BeerDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {
    private val beersDao: BeersDao = database.beersDao()

    override suspend fun getBeersFromInternet(page: String, perPage: String): List<BeerNetwork> =
        withContext(dispatcher) {
            API.beers.getBeers(page, perPage)
        }

    override suspend fun getBeersFromDatabase(): List<BeerEntity> = withContext(dispatcher) {
        beersDao.getBeers()
    }

    override suspend fun getBeers(page: String, forceUpdate: Boolean): Result<List<Beer>> {
        return if (forceUpdate) {
            try {
                val beersNetwork = getBeersFromInternet(page, "20")
                saveBeersFromInternet(beersNetwork)
                val beers = beersNetwork.map { it.asDomain() }
                Result.Success(beers)
            } catch (e: Exception) {
                Result.Error(e)
            }
        } else {
            val beersEntities = getBeersFromDatabase()
            if (beersEntities.isNotEmpty()) {
                val beers = beersEntities.map { it.asDomain() }
                Result.Success(beers)
            } else {
                Result.Success(emptyList())
            }
        }
    }

    override suspend fun saveBeersFromInternet(beers: List<BeerNetwork>) = withContext(dispatcher) {
        val beersEntities = beers.map { it.asEntity() }.toTypedArray()
        beersDao.saveBeers(*beersEntities)
    }
}