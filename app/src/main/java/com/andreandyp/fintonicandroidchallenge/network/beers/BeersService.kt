package com.andreandyp.fintonicandroidchallenge.network.beers

import com.andreandyp.fintonicandroidchallenge.network.BeerNetwork
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersService {

    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: String,
        @Query("per_page") perPage: String
    ): List<BeerNetwork>
}