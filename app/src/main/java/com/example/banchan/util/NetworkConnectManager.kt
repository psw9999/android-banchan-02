package com.example.banchan.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectManager @Inject constructor(
    @ApplicationContext context: Context
) : DefaultLifecycleObserver {
    private var isLastStateDisconnect = false
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(ConnectivityManager::class.java)
    private val networkInfo =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    private var onConnect: () -> Unit = {}
    private var onDisconnect: () -> Unit = {}

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            if (isLastStateDisconnect) {
                onConnect()
                isLastStateDisconnect = false
            }
        }

        override fun onLost(network: Network) {
            isLastStateDisconnect = true
            onDisconnect()
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    fun init(onConnectAction: () -> Unit, onDisconnectAction: () -> Unit) {
        onConnect = onConnectAction
        onDisconnect = onDisconnectAction

        if ((networkInfo?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                    networkInfo?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true).not()
        ) {
            isLastStateDisconnect = true
            onDisconnect()
        }
    }
}