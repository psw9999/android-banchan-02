package com.example.banchan.presentation.basket

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.os.SystemClock
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.example.banchan.AlarmReceiver
import com.example.banchan.AlarmReceiver.Companion.ID
import com.example.banchan.R
import com.example.banchan.databinding.FragmentBasketBinding
import com.example.banchan.domain.model.BasketModel
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.UiState
import com.example.banchan.presentation.adapter.basket.BasketListAdapter
import com.example.banchan.presentation.adapter.basket.BasketOrderAdapter
import com.example.banchan.presentation.adapter.basket.BasketRecentlyTabAdapter
import com.example.banchan.presentation.adapter.basket.BasketTabAdapter
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.main.BasketViewModel
import com.example.banchan.presentation.ordersuccess.OrderSuccessFragment
import com.example.banchan.presentation.productdetail.ProductDetailFragment
import com.example.banchan.presentation.recentlyproduct.RecentlyProductFragment
import com.example.banchan.presentation.recentlyproduct.RecentlyProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding>(R.layout.fragment_basket) {
    private val recentlyProductViewModel: RecentlyProductViewModel by activityViewModels()
    private val basketViewModel: BasketViewModel by activityViewModels()
    private val basketListViewModel: BasketListViewModel by activityViewModels()

    private val onCheckBoxClick: ((BasketModel) -> Unit) = { basketModel ->
        basketListViewModel.updateBasketItem(basketModel)
    }

    private val onClickCheckBoxTab: ((Int) -> Unit) = { isSelected ->
        basketListViewModel.updateAllBasketIsSelected(isSelected)
    }

    private val onClickSelectedDeleteBtn: (() -> Unit) = {
        basketListViewModel.deleteSelectedBasketItems()
    }

    private val onClickDeleteBtn: ((BasketModel) -> Unit) = { basketModel ->
        basketListViewModel.deleteBasketItem(basketModel)
    }

    private val onClickMinusBtn: ((BasketModel) -> Unit) = { basketModel ->
        basketListViewModel.decreaseBasketCount(basketModel)
    }

    private val onClickPlusBtn: ((BasketModel) -> Unit) = { basketModel ->
        basketListViewModel.increaseBasketCount(basketModel)
    }

    private val basketListTabAdapter by lazy {
        BasketTabAdapter(
            onClickCheckBoxTab,
            onClickSelectedDeleteBtn
        )
    }
    private val basketListAdapter by lazy {
        BasketListAdapter(
            onCheckBoxClick,
            onClickDeleteBtn,
            onClickMinusBtn,
            onClickPlusBtn
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
                    basketListViewModel.basketItemFlow.collectLatest {
                        basketListAdapter.submitList(it)
                        basketListViewModel.setIsBasketLoading(false)
                    }
                }

                launch {
                    recentlyProductViewModel.uiState.collect {
                        when (it) {
                            is UiState.Success -> {
                                basketRecentlyTabAdapter.setRecentlyViewedList(it.item.take(7))
                                basketListViewModel.setIsRecentlyLoading(false)
                            }
                            else -> {}
                        }
                    }
                }

                launch {
//                    basketListViewModel.isLoading.collectLatest { isLoading ->
//                        if (isLoading) {
//                            binding.pbBasketLoading.visibility = View.VISIBLE
//                            binding.rvBasketList.visibility = View.INVISIBLE
//                        } else {
//                            binding.pbBasketLoading.visibility = View.INVISIBLE
//                            binding.rvBasketList.visibility = View.VISIBLE
//                        }
//                    }
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
        binding.rvBasketList.adapter = ConcatAdapter(
            basketListTabAdapter,
            basketListAdapter,
            basketOrderAdapter,
            basketRecentlyTabAdapter
        )
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