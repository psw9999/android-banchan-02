package com.example.banchan.util.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.banchan.R

@BindingAdapter("imageUrl")
fun ImageView.setImageFromUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_baseline_restaurant_menu_24)
        .error(R.drawable.ic_baseline_error_24)
        .into(this)
}

@BindingAdapter("sectionUrl")
fun ImageView.setSectionImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_baseline_restaurant_menu_24)
        .skipMemoryCache(true)
        .error(R.drawable.ic_baseline_error_24)
        .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
        .into(this)
}