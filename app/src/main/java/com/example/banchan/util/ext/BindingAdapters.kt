package com.example.banchan.util.ext

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.banchan.R
import com.example.banchan.presentation.UiState

@BindingAdapter("imageUrl")
fun ImageView.setImageFromUrl(url: String?) {
    url?.let {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.ic_baseline_error_24)
            .into(this)
    }
}

@BindingAdapter("sectionUrl")
fun ImageView.setSectionImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.placeholder)
        .skipMemoryCache(true)
        .error(R.drawable.ic_baseline_error_24)
        .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
        .into(this)
}

@BindingAdapter("showOnSuccess")
fun showOnSuccess(view: View, uiState: UiState<*>) {
    view.visibility = if (uiState !is UiState.Error) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("showOnLoading")
fun showOnLoading(view: View, uiState: UiState<*>) {
    view.visibility = if (uiState is UiState.Loading) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("showOnError")
fun showOnError(view: View, uiState: UiState<*>) {
    view.visibility = if (uiState is UiState.Error) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("showOnEmpty")
fun showOnEmpty(view: View, uiState: UiState<*>) {
    view.visibility = if (uiState is UiState.Empty) View.VISIBLE else View.INVISIBLE
}
