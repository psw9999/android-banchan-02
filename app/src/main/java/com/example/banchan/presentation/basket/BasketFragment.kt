package com.example.banchan.presentation.basket

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.os.SystemClock
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.example.banchan.AlarmReceiver
import com.example.banchan.R
import com.example.banchan.databinding.FragmentBasketBinding
import com.example.banchan.domain.model.BasketModel
import com.example.banchan.presentation.adapter.basket.BasketListAdapter
import com.example.banchan.presentation.adapter.basket.BasketOrderAdapter
import com.example.banchan.presentation.adapter.basket.BasketRecentlyTabAdapter
import com.example.banchan.presentation.adapter.basket.BasketTabAdapter
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.main.FragmentType
import com.example.banchan.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding>(R.layout.fragment_basket) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val basketViewModel: BasketViewModel by activityViewModels()

    private val onCheckBoxClick: ((BasketModel) -> Unit) = { basketModel ->
        basketViewModel.updateBasketItem(basketModel)
    }

    private val onClickCheckBoxTab: ((Int) -> Unit) = { isSelected ->
        basketViewModel.updateAllBasketIsSelected(isSelected)
    }

    private val onClickSelectedDeleteBtn: (() -> Unit) = {
        basketViewModel.deleteSelectedBasketItems()
    }

    private val onClickDeleteBtn: ((BasketModel) -> Unit) = { basketModel ->
        basketViewModel.deleteBasketItem(basketModel)
    }

    private val onClickMinusBtn: ((BasketModel) -> Unit) = { basketModel ->
        basketViewModel.decreaseBasketCount(basketModel)
    }

    private val onClickPlusBtn: ((BasketModel) -> Unit) = { basketModel ->
        basketViewModel.increaseBasketCount(basketModel)
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
    private val basketOrderAdapter by lazy { BasketOrderAdapter() }
    private val basketRecentlyTabAdapter by lazy {
        BasketRecentlyTabAdapter {
            mainViewModel.setCurrentFragment(FragmentType.RecentlyViewedProduct)
        }
    }

    override fun initViews() {
        initRecyclerView()
        binding.tbBasketBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    basketViewModel.basketItemFlow.collectLatest {
                        basketListAdapter.submitList(it)
                        basketViewModel.setIsBasketLoading(false)
                    }
                }

                launch {
                    basketViewModel.recentlyProductFlow.collectLatest {
                        basketRecentlyTabAdapter.setRecentlyViewedList(it)
                        basketViewModel.setIsRecentlyLoading(false)
                    }
                }

                launch {
                    basketViewModel.isLoading.collectLatest { isLoading ->
                        if (isLoading) {
                            binding.pbBasketLoading.visibility = View.VISIBLE
                            binding.rvBasketList.visibility = View.INVISIBLE
                        } else {
                            binding.pbBasketLoading.visibility = View.INVISIBLE
                            binding.rvBasketList.visibility = View.VISIBLE
                        }
                    }
                }

                launch {
                    basketViewModel.basketAmountSumFlow.collectLatest {
                        basketOrderAdapter.setOrderModel(it)
                    }
                }

                launch {
                    basketViewModel.isAllBasketItemSelectedFlow.collectLatest {
                        println(it)
                        basketListTabAdapter.setIsAllBasketItemSelected(it)
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
    }

    private fun makeAlarm() {
        requireContext().run {
            val alarmManager = getSystemService(Application.ALARM_SERVICE) as AlarmManager
            val triggerTime = (SystemClock.elapsedRealtime() + AlarmReceiver.ALARM_TIMER)
            val pendingIntent = PendingIntent.getBroadcast(
                this, triggerTime.toInt(), Intent(this, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.setExact(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }
}