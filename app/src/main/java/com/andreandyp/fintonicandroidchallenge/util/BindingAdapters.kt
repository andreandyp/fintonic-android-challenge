package com.andreandyp.fintonicandroidchallenge.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.andreandyp.fintonicandroidchallenge.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String) {
    Picasso
        .get()
        .load(url)
        .error(R.drawable.ic_baseline_cloud_off_24)
        .placeholder(R.drawable.ic_baseline_cloud_download_24)
        .into(this)
}

@BindingAdapter("refreshing")
fun SwipeRefreshLayout.setRefresh(refreshing: Boolean) {
    isRefreshing = refreshing
}