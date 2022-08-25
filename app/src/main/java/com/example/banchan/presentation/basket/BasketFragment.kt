package com.example.banchan.presentation.basket

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.os.SystemClock
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.example.banchan.AlarmReceiver
import com.example.banchan.AlarmReceiver.Companion.ID
import com.example.banchan.R
import com.example.banchan.databinding.FragmentBasketBinding
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.UiState
import com.example.banchan.presentation.adapter.basket.*
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.main.BasketViewModel
import com.example.banchan.presentation.ordersuccess.OrderSuccessFragment
import com.example.banchan.presentation.productdetail.ProductDetailFragment
import com.example.banchan.presentation.recentlyproduct.RecentlyProductFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding>(R.layout.fragment_basket) {
    private val recentlyProductViewModel: BasketRecentlyProductViewModel by viewModels()
    private val basketViewModel: BasketViewModel by activityViewModels()
    private val basketListViewModel: BasketListViewModel by viewModels()

    private val basketListTabAdapter by lazy {
        BasketTabAdapter(
            { isSelected -> basketListViewModel.updateAllBasketIsSelected(isSelected) },
            { basketListViewModel.deleteSelectedBasketItems() }
        )
    }

    private val basketListAdapter by lazy {
        BasketListAdapter(
            { basketModel -> basketListViewModel.updateBasketItem(basketModel) },
            { basketModel -> basketListViewModel.deleteBasketItem(basketModel) },
            { basketModel -> basketListViewModel.decreaseBasketCount(basketModel) },
            { basketModel -> basketListViewModel.increaseBasketCount(basketModel) }
        )
    }

    private val basketEmptyAdapter by lazy { BasketEmptyAdapter() }
    private val basketLoadingAdapter by lazy { BasketLoadingAdapter() }
    private val basketErrorAdapter by lazy { BasketErrorAdapter{ basketListViewModel.refresh() } }

    private val basketConcatAdapter by lazy {
        ConcatAdapter(
            basketListTabAdapter,
            basketLoadingAdapter,
            basketRecentlyTabAdapter
        )
    }

    private val basketOrderAdapter by lazy {
        BasketOrderAdapter { deliveryFee -> basketListViewModel.insertHistoryItemList(deliveryFee) }
    }
    private val basketRecentlyTabAdapter by lazy {
        BasketRecentlyTabAdapter(
            onClickRecentlyTab = {
                navigateToRecent()
            }, onItemClick = { itemModel ->
                navigateToDetail(itemModel)
            }, onRefreshBtnClick = {
                recentlyProductViewModel.refresh()
            }
        )
    }

    override fun initViews() {
        initRecyclerView()
        binding.tbBasketBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    basketListViewModel.basketUiState.collect { basketUiState ->
                        val currentAdapter = basketConcatAdapter.adapters[1]
                        when(basketUiState) {
                            is UiState.Init, UiState.Loading -> {
                                if(currentAdapter != basketLoadingAdapter) {
                                    basketConcatAdapter.removeAdapter(currentAdapter)
                                    basketConcatAdapter.removeAdapter(basketOrderAdapter)
                                    basketConcatAdapter.addAdapter(1, basketLoadingAdapter)
                                }
                            }
                            is UiState.Success -> {
                                basketListAdapter.submitList(basketUiState.item)
                                if(currentAdapter != basketListAdapter) {
                                    basketConcatAdapter.removeAdapter(currentAdapter)
                                    basketConcatAdapter.addAdapter(1, basketListAdapter)
                                    basketConcatAdapter.addAdapter(2, basketOrderAdapter)
                                }
                            }
                            is UiState.Empty -> {
                                if(currentAdapter != basketEmptyAdapter) {
                                    basketConcatAdapter.removeAdapter(currentAdapter)
                                    basketConcatAdapter.removeAdapter(basketOrderAdapter)
                                    basketConcatAdapter.addAdapter(1, basketEmptyAdapter)
                                }
                            }
                            is UiState.Error -> {
                                if(currentAdapter != basketErrorAdapter) {
                                    basketConcatAdapter.removeAdapter(currentAdapter)
                                    basketConcatAdapter.removeAdapter(basketOrderAdapter)
                                    basketConcatAdapter.addAdapter(1, basketErrorAdapter)
                                }
                            }
                        }
                    }
                }

                launch {
                    recentlyProductViewModel.uiState.collect {
                        basketRecentlyTabAdapter.setRecentlyViewedList(it)
                    }
                }

                launch {
                    basketListViewModel.basketAmountSumFlow.collectLatest {
                        basketOrderAdapter.setOrderModel(it)
                    }
                }

                launch {
                    basketListViewModel.isAllBasketItemSelectedFlow.collectLatest {
                        basketListTabAdapter.setIsAllBasketItemSelected(it)
                    }
                }

                launch {
                    basketListViewModel.successHistoryId.collect {
                        makeAlarm(it)
                        navigateToOrderSuccess(it)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvBasketList.adapter = basketConcatAdapter
        binding.rvBasketList.itemAnimator = null
    }

    private fun makeAlarm(id: Long) {
        requireContext().run {
            val alarmManager = getSystemService(Application.ALARM_SERVICE) as AlarmManager
            val triggerTime = (SystemClock.elapsedRealtime() + AlarmReceiver.ALARM_TIMER)
            val pendingIntent = PendingIntent.getBroadcast(
                this, triggerTime.toInt(), Intent(this, AlarmReceiver::class.java).apply {
                    putExtra(ID, id)
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.setExact(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }

    private fun navigateToRecent() {
        parentFragmentManager.run {
            popBackStack(
                RecentlyProductFragment.TAG,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            commit {
                replace(
                    R.id.layout_main_container,
                    RecentlyProductFragment(),
                    RecentlyProductFragment.TAG
                )
                addToBackStack(RecentlyProductFragment.TAG)
            }
        }
    }

    private fun navigateToDetail(itemModel: ItemModel) {
        basketViewModel.setSelectedBasketItem(itemModel)
        parentFragmentManager.run {
            popBackStack(
                ProductDetailFragment.TAG,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            commit {
                replace(
                    R.id.layout_main_container,
                    ProductDetailFragment(),
                    ProductDetailFragment.TAG
                )
                addToBackStack(ProductDetailFragment.TAG)
            }
        }
    }

    private fun navigateToOrderSuccess(id: Long) {
        parentFragmentManager.run {
            popBackStack()
            commit {
                replace(
                    R.id.layout_main_container,
                    OrderSuccessFragment.newInstance(id)
                )
                addToBackStack(OrderSuccessFragment.TAG)
            }
        }
    }

    companion object {
        const val TAG = "basket"
    }
}