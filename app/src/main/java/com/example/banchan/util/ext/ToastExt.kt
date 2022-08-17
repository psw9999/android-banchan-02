package com.example.banchan.util.ext

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String) =
    Toast.makeText(this, message, 1000).show()