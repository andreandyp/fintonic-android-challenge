package com.andreandyp.fintonicandroidchallenge.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.andreandyp.fintonicandroidchallenge.network.beers.BeersService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BEERS_URL = "https://api.punkapi.com/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofitBeers = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BEERS_URL)
    .build()

object API {
    val beers: BeersService by lazy {
        retrofitBeers.create(BeersService::class.java)
    }
}