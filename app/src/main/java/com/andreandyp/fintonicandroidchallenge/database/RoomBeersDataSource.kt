package com.andreandyp.fintonicandroidchallenge.database

import com.andreandyp.fintonicandroidchallenge.database.entities.asDomain
import com.andreandyp.fintonicandroidchallenge.database.entities.asEntity
import com.andreandyp.fintonicandroidchallenge.datasources.LocalBeersDataSource
import com.andreandyp.fintonicandroidchallenge.domain.Beer

class RoomBeersDataSource(private val beersDao: BeersDao) : LocalBeersDataSource {
    override suspend fun getBeers(): List<Beer> {
        val beersRoom = beersDao.getBeers()
        return if (beersRoom.isNotEmpty()) beersRoom.map { it.asDomain() } else emptyList()
    }

    override suspend fun saveBeers(beers: List<Beer>) {
        val beersEntities = beers.map { it.asEntity() }.toTypedArray()
        beersDao.saveBeers(*beersEntities)
    }
}