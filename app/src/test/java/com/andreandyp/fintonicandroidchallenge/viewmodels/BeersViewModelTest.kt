package com.andreandyp.fintonicandroidchallenge.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.andreandyp.fintonicandroidchallenge.domain.Beer
import com.andreandyp.fintonicandroidchallenge.repository.BeerRepository
import com.andreandyp.fintonicandroidchallenge.repository.Result
import com.andreandyp.fintonicandroidchallenge.repository.fakes.FakeBeerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BeersViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var beerRepository: BeerRepository
    private val dispatcher = TestCoroutineDispatcher()
    private val repoBeers = listOf(
        Beer(0, "", "", "", "", "", ""),
        Beer(1, "", "", "", "", "", ""),
    )


    @Before
    fun setUp() {
        beerRepository = FakeBeerRepository(repoBeers)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `should change status when viewmodel is loaded`() {
        val events = mutableListOf<Result<List<Beer>>>()
        val viewModel = BeersViewModel(beerRepository)

        val observer = Observer(events::add)
        viewModel.status.observeForever(observer)

        viewModel.getBeers()

        assertThat(events[0], IsInstanceOf(Result.Success::class.java))
        viewModel.status.removeObserver(observer)
    }

    @Test
    fun `should change status when beers are requested`() {
        val events = mutableListOf<Result<List<Beer>>>()
        val viewModel = BeersViewModel(beerRepository)

        val observer = Observer(events::add)
        viewModel.status.observeForever(observer)

        // Remove extra call of init block
        events.removeAt(events.size - 1)

        viewModel.getBeers()

        assertThat(events[0], IsInstanceOf(Result.Loading::class.java))
        assertThat(events[1], IsInstanceOf(Result.Success::class.java))
        viewModel.status.removeObserver(observer)
    }

    @Test
    fun `should change status when more beers are requested`() {
        val events = mutableListOf<Result<List<Beer>>>()
        val viewModel = BeersViewModel(beerRepository)

        val observer = Observer(events::add)
        viewModel.status.observeForever(observer)

        // Remove extra call of init block
        events.removeAt(events.size - 1)

        viewModel.loadMoreBeers()
        assertThat(events[0], IsInstanceOf(Result.Loading::class.java))
        assertThat(events[1], IsInstanceOf(Result.Success::class.java))
        viewModel.status.removeObserver(observer)
    }

    @After
    fun resetMain() {
        Dispatchers.resetMain()
    }
}