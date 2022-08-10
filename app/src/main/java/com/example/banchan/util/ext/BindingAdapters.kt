package com.example.banchan.util.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.setImageFromUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}