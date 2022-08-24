package com.example.banchan.presentation.orderlist

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.databinding.FragmentOrderListBinding
import com.example.banchan.presentation.adapter.orderlist.OrderListAdapter
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.home.OrderStateViewModel
import com.example.banchan.presentation.ordersuccess.OrderSuccessFragment
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderListFragment : BaseFragment<FragmentOrderListBinding>(R.layout.fragment_order_list) {
    private val orderStateViewModel: OrderStateViewModel by activityViewModels()
    private val orderListAdapter by lazy {
        OrderListAdapter {
            navigateToOrderSuccess(it)
        }
    }

    private fun navigateToOrderSuccess(id: Long) {
        parentFragmentManager.run {
            commit {
                replace(
                    R.id.layout_main_container,
                    OrderSuccessFragment.newInstance(id)
                )
                addToBackStack(OrderSuccessFragment.TAG)
            }
        }
    }

    override fun initViews() {
        initRecyclerView()
        binding.tbOrderListBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                orderStateViewModel.historyModelFlow.collectLatest {
                    orderListAdapter.submitList(it)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvOrderList.adapter = orderListAdapter
        binding.rvOrderList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = dpToPx(requireContext(), 8)
            }
        })
    }

    companion object {
        const val TAG = "order_list"
    }
}