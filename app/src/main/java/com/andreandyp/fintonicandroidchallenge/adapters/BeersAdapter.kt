package com.andreandyp.fintonicandroidchallenge.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.andreandyp.fintonicandroidchallenge.R
import com.andreandyp.fintonicandroidchallenge.databinding.ItemBeerBinding
import com.andreandyp.fintonicandroidchallenge.domain.Beer
import com.andreandyp.fintonicandroidchallenge.fragments.BeersFragmentDirections

class BeersAdapter(
    private val navController: NavController,
    var beers: List<Beer> = emptyList()
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val showBeerDetails: View.OnClickListener = View.OnClickListener { v ->
        val position = v.tag as Int
        val beer = beers[position]
        val directions = BeersFragmentDirections
        navController.navigate(directions.getBeerDetails(beer))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LOADING_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            return LoadingViewHolder(view)
        }

        val binding: ItemBeerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_beer,
            parent,
            false
        )

        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val beer = beers[position]
        if (holder is BeerViewHolder) {
            holder.binding.beer = beer
            with(holder.itemView) {
                tag = position
                setOnClickListener(showBeerDetails)
            }
        }
    }

    override fun getItemCount() = beers.size

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) LOADING_ITEM else BEER_ITEM
    }

    companion object {
        private const val BEER_ITEM: Int = 0
        private const val LOADING_ITEM: Int = 1
    }

    inner class BeerViewHolder(val binding: ItemBeerBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class LoadingViewHolder(view: View) :
        RecyclerView.ViewHolder(view)
}