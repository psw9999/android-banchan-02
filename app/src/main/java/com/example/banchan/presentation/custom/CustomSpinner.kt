package com.example.banchan.presentation.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner
import com.example.banchan.R

class CustomSpinner(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatSpinner(context, attrs) {

    private var isOpened = false

    override fun performClick(): Boolean {
        isOpened = true
        setBackgroundResource(R.drawable.spinner_open)
        return super.performClick()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (isOpened && hasWindowFocus) {
            isOpened = false
            setBackgroundResource(R.drawable.spinner_close)
        }
        super.onWindowFocusChanged(hasWindowFocus)
    }
}