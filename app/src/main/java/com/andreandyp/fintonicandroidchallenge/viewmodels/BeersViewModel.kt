package com.andreandyp.fintonicandroidchallenge.viewmodels

import androidx.lifecycle.*
import com.andreandyp.fintonicandroidchallenge.domain.Beer
import com.andreandyp.fintonicandroidchallenge.repository.BeerRepository
import com.andreandyp.fintonicandroidchallenge.repository.Result
import kotlinx.coroutines.launch

class BeersViewModel(private val repository: BeerRepository) : ViewModel() {
    private val _status = MutableLiveData<Result<List<Beer>>>()
    val status: LiveData<Result<List<Beer>>>
        get() = _status

    private val _beers = MutableLiveData<List<Beer>>()
    val beers: LiveData<List<Beer>>
        get() = _beers

    private var currentPage = 1

    init {
        _beers.value = emptyList()
        getBeers()
    }

    fun getBeers(forceUpdate: Boolean = true) {
        _status.value = Result.Loading
        viewModelScope.launch {
            val result = repository.getBeers(currentPage, forceUpdate)
            if (result is Result.Success) {
                val elements = _beers.value!!.toMutableSet()
                elements.addAll(result.data)
                _beers.postValue(elements.toList())
            } else {
                result as Result.Error
            }
            _status.value = result
        }
    }

    fun loadMoreBeers() {
        ++currentPage
        getBeers()
    }

    @Suppress("UNCHECKED_CAST")
    class BeerListViewModelFactory(
        private val beerRepository: BeerRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            BeersViewModel(beerRepository) as T
    }
}