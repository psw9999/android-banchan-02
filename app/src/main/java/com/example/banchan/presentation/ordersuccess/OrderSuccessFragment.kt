package com.example.banchan.presentation.ordersuccess

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.os.SystemClock
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.example.banchan.AlarmReceiver
import com.example.banchan.R
import com.example.banchan.databinding.FragmentOrderSuccessBinding
import com.example.banchan.presentation.adapter.common.CommonOrderFooterAdapter
import com.example.banchan.presentation.adapter.common.CommonOrderHeaderAdapter
import com.example.banchan.presentation.adapter.ordersuccess.OrderSuccessItemAdapter
import com.example.banchan.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderSuccessFragment :
    BaseFragment<FragmentOrderSuccessBinding>(R.layout.fragment_order_success) {
    val viewModel by viewModels<OrderSuccessViewModel>()
    private val headerAdapter = CommonOrderHeaderAdapter()
    private val itemAdapter = OrderSuccessItemAdapter()
    private val footerAdapter = CommonOrderFooterAdapter()

    override fun initViews() {
        binding.rvOrderSuccess.apply {
            itemAnimator = null
            adapter = ConcatAdapter(
                headerAdapter,
                itemAdapter,
                footerAdapter
            )
        }

        binding.toolbarRefresh.setOnClickListener {
            viewModel.refresh()
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.headerUiState.collect {
                        it?.let {
                            headerAdapter.updateHeader(it)
                        }
                    }
                }
                launch {
                    viewModel.itemsUiState.collect {
                        it?.let {
                            itemAdapter.submitList(it)
                        }
                    }
                }
                launch {
                    launch {
                        viewModel.footerUiState.collect {
                            it?.let {
                                footerAdapter.updateFooter(it)
                            }
                        }
                    }
                }
            }
        }
    }
}