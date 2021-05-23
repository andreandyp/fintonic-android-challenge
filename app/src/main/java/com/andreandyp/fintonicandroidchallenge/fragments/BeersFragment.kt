package com.andreandyp.fintonicandroidchallenge.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.andreandyp.fintonicandroidchallenge.R
import com.andreandyp.fintonicandroidchallenge.adapters.BeersAdapter
import com.andreandyp.fintonicandroidchallenge.database.BeerDatabase
import com.andreandyp.fintonicandroidchallenge.databinding.FragmentBeersBinding
import com.andreandyp.fintonicandroidchallenge.domain.Beer
import com.andreandyp.fintonicandroidchallenge.repository.BeerRepository
import com.andreandyp.fintonicandroidchallenge.repository.Result
import com.andreandyp.fintonicandroidchallenge.viewmodels.BeersViewModel
import com.google.android.material.snackbar.Snackbar

class BeersFragment : Fragment() {
    private lateinit var beerListAdapter: BeersAdapter
    private lateinit var beerListBinding: FragmentBeersBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyListTextView: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val beersViewModel by viewModels<BeersViewModel> {
        val repository = BeerRepository(BeerDatabase.getDatabase(requireContext()))
        BeersViewModel.BeerListViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        beerListBinding = FragmentBeersBinding.inflate(layoutInflater, container, false)
        return beerListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beerListBinding.vm = beersViewModel
        beerListBinding.lifecycleOwner = viewLifecycleOwner

        emptyListTextView = beerListBinding.tvEmptyList
        recyclerView = beerListBinding.rcBeersList
        swipeRefreshLayout = beerListBinding.swipeLayoutBeersList

        swipeRefreshLayout.setOnRefreshListener {
            beersViewModel.getBeers()
        }

        setupAdapter(findNavController(), beersViewModel.beers.value!!)
        setupRecyclerView()
        setupLiveDataObservers()
    }

    private fun setupAdapter(navController: NavController, beers: List<Beer>) {
        beerListAdapter = BeersAdapter(
            navController,
            beers
        )
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = beerListAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                loadMoreBeers(recyclerView)
            }
        })
    }

    private fun loadMoreBeers(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
        val items = layoutManager.itemCount

        if (items == lastPosition + 1) {
            beersViewModel.loadMoreBeers()
        }
    }

    private fun setupLiveDataObservers() {
        beersViewModel.beers.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                emptyListTextView.text = getString(R.string.empty_db)
            }

            beerListAdapter.beers = it
            beerListAdapter.notifyDataSetChanged()
        })

        beersViewModel.status.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Loading -> {
                    swipeRefreshLayout.isRefreshing = true
                    emptyListTextView.text = getString(R.string.loading)
                }
                is Result.Error -> {
                    val noInternetMessage = getString(R.string.no_internet_connection_error)
                    val retryMessage = getString(R.string.get_cache_action)
                    showSnackbar(noInternetMessage, retryMessage) {
                        beersViewModel.getBeers(false)
                    }
                    emptyListTextView.text = getString(R.string.empty_db)

                    swipeRefreshLayout.isRefreshing = false
                }
                else -> swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun showSnackbar(
        text: String,
        actionText: String?,
        action: ((view: View) -> Unit)? = null
    ) {
        val snackbar = Snackbar.make(beerListBinding.root, text, 3000)
        snackbar.setAction(actionText, action)
        snackbar.show()
    }
}