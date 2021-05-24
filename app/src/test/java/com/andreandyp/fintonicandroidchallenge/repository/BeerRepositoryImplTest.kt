package com.andreandyp.fintonicandroidchallenge.repository

import com.andreandyp.fintonicandroidchallenge.domain.Beer
import com.andreandyp.fintonicandroidchallenge.repository.fakes.FakeLocalBeersDataSource
import com.andreandyp.fintonicandroidchallenge.repository.fakes.FakeRemoteBeersDataSource
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.isA
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Test

class BeerRepositoryImplTest {
    private lateinit var repository: BeerRepository
    private lateinit var fakeLocalBeersDataSource: FakeLocalBeersDataSource
    private lateinit var remoteBeersDataSource: FakeRemoteBeersDataSource
    private val localBeers = mutableListOf(
        Beer(0, "", "", "", "", "", ""),
        Beer(1, "", "", "", "", "", ""),
    )

    private val remoteBeers = listOf(
        Beer(2, "", "", "", "", "", ""),
        Beer(3, "", "", "", "", "", ""),
    )

    @Before
    fun setUp() {
        fakeLocalBeersDataSource = FakeLocalBeersDataSource(localBeers)
        remoteBeersDataSource = FakeRemoteBeersDataSource(remoteBeers)
        repository = BeerRepositoryImpl(fakeLocalBeersDataSource, remoteBeersDataSource)
    }

    @Test
    fun `should get beers from local source`() = runBlocking {
        val localBeers = repository.getBeersFromLocal()
        assertThat(localBeers, containsInAnyOrder(*localBeers.toTypedArray()))
    }

    @Test
    fun `should get beers from remote source`() = runBlocking {
        val remoteBeers = repository.getBeersFromRemote(1, 10)
        assertThat(remoteBeers, containsInAnyOrder(*remoteBeers.toTypedArray()))
    }

    @Test
    fun `should save beers from remote source`() = runBlocking {
        repository.saveBeersToLocal(remoteBeers)
        val local = repository.getBeersFromLocal()
        assertThat(local, hasItem(remoteBeers[0]))
        assertThat(local, hasItem(remoteBeers[1]))
    }

    @Test
    fun `should get beers from remote when forced`() = runBlocking {
        val result = repository.getBeers(1, true)
        assertThat(result, isA(Result::class.java))
        assertThat(result, IsInstanceOf(Result.Success::class.java))
        val data = (result as Result.Success).data
        assertThat(data, contains(*remoteBeers.toTypedArray()))
    }

    @Test
    fun `should get beers from local when not forced`() = runBlocking {
        val result = repository.getBeers(1, false)
        assertThat(result, isA(Result::class.java))
        assertThat(result, IsInstanceOf(Result.Success::class.java))
        val data = (result as Result.Success).data
        assertThat(data, contains(*localBeers.toTypedArray()))
    }

    @Test
    fun `should return an error when an exception happens in remote source`() = runBlocking {
        remoteBeersDataSource.throwError = true
        val result = repository.getBeers(1, true)
        assertThat(result, isA(Result::class.java))
        assertThat(result, IsInstanceOf(Result.Error::class.java))
        val error = (result as Result.Error).exception
        assertThat(error, isA(Exception::class.java))
    }

    @Test
    fun `should return an error when an exception happens in local source`() = runBlocking {
        fakeLocalBeersDataSource.throwError = true
        val result = repository.getBeers(1, false)
        assertThat(result, isA(Result::class.java))
        assertThat(result, IsInstanceOf(Result.Error::class.java))
        val error = (result as Result.Error).exception
        assertThat(error, isA(Exception::class.java))
    }
}