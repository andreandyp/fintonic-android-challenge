package com.andreandyp.fintonicandroidchallenge.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.andreandyp.fintonicandroidchallenge.R
import com.andreandyp.fintonicandroidchallenge.databinding.FragmentBeerBinding
import com.andreandyp.fintonicandroidchallenge.databinding.FragmentBeersBinding

class BeerFragment : Fragment() {
    private val args: BeerFragmentArgs by navArgs()
    private lateinit var binding: FragmentBeerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beer, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.beer = args.beer
        binding.lifecycleOwner = viewLifecycleOwner
        (activity as AppCompatActivity).supportActionBar?.title = args.beer.name
    }
}