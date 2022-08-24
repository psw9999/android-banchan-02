package com.example.banchan.presentation.ordersuccess

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.example.banchan.R
import com.example.banchan.databinding.FragmentOrderSuccessBinding
import com.example.banchan.presentation.UiState
import com.example.banchan.presentation.adapter.common.CommonOrderFooterAdapter
import com.example.banchan.presentation.adapter.common.CommonOrderHeaderAdapter
import com.example.banchan.presentation.adapter.ordersuccess.OrderSuccessItemAdapter
import com.example.banchan.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OrderSuccessFragment :
    BaseFragment<FragmentOrderSuccessBinding>(R.layout.fragment_order_success) {
    @Inject
    lateinit var factory: OrderSuccessViewModel.IdAssistedFactory
    val viewModel by viewModels<OrderSuccessViewModel> {
        OrderSuccessViewModel.provideFactory(
            assistedFactory = factory,
            id = arguments?.getLong(KEY_ID, 0) ?: 0
        )
    }
    private val headerAdapter = CommonOrderHeaderAdapter()
    private val itemAdapter = OrderSuccessItemAdapter()
    private val footerAdapter = CommonOrderFooterAdapter()

    override fun initViews() {
        binding.viewModel = viewModel

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

        binding.toolbarBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        when (it) {
                            is UiState.Success -> {
                                headerAdapter.updateHeader(it.item.header)
                                itemAdapter.submitList(it.item.body.historyItem)
                                footerAdapter.updateFooter(it.item.footer)
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "order_success"

        private const val KEY_ID = "id"

        fun newInstance(id: Long): OrderSuccessFragment {
            val fragment = OrderSuccessFragment()

            val args = Bundle()
            args.putLong(KEY_ID, id)
            fragment.arguments = args

            return fragment
        }
    }
}