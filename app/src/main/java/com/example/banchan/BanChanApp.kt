package com.example.banchan

import android.app.Application
import android.content.Intent
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BanChanApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startService(Intent(this, OrderService::class.java))
    }
}