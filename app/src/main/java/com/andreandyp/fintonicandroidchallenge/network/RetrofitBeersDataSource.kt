package com.andreandyp.fintonicandroidchallenge.network

import com.andreandyp.fintonicandroidchallenge.datasources.RemoteBeersDataSource
import com.andreandyp.fintonicandroidchallenge.domain.Beer
import com.andreandyp.fintonicandroidchallenge.network.beers.BeersService

class RetrofitBeersDataSource(private val beersService: BeersService) : RemoteBeersDataSource {
    override suspend fun getBeers(page: Int, beersPerPage: Int): List<Beer> {
        val beersNetwork = beersService.getBeers(page.toString(), beersPerPage.toString())
        return beersNetwork.map { it.asDomain() }
    }
}