package com.example.banchan.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.banchan.AlarmReceiver.Companion.OPEN_ODER_ID
import com.example.banchan.R
import com.example.banchan.databinding.ActivityMainBinding
import com.example.banchan.presentation.home.HomeFragment
import com.example.banchan.presentation.ordersuccess.OrderSuccessFragment
import com.example.banchan.util.NetworkConnectManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var networkConnectManager: NetworkConnectManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()

        networkConnectManager.init(
            onConnectAction = {
                Snackbar.make(
                    binding.root,
                    getString(R.string.network_enable),
                    Snackbar.LENGTH_LONG
                ).show()
            },
            onDisconnectAction = {
                Snackbar.make(
                    binding.root,
                    getString(R.string.network_disable),
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            })
        lifecycle.addObserver(networkConnectManager)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.layout_main_container, HomeFragment())
            }
            onNewIntent(intent)
        }
    }

    private fun observe() {
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val id = intent?.getLongExtra(OPEN_ODER_ID, -1)
        if (id != null && id != -1L) {
            supportFragmentManager.run {
                commit {
                    popBackStack(
                        OrderSuccessFragment.TAG,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    replace(
                        R.id.layout_main_container,
                        OrderSuccessFragment.newInstance(id)
                    )
                    addToBackStack(null)
                }
            }
        }
    }
}