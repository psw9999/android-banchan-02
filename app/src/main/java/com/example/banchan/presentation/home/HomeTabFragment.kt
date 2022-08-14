package com.example.banchan.presentation.home

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.dialog.BasketBottomSheet
import com.example.banchan.presentation.main.BasketViewModel
import com.example.banchan.presentation.main.FragmentType
import com.example.banchan.presentation.main.MainViewModel

abstract class HomeTabFragment<DB : ViewDataBinding>(@LayoutRes val layout: Int) :
    BaseFragment<DB>(layout) {

    protected val mainViewModel: MainViewModel by activityViewModels()
    protected val basketViewModel: BasketViewModel by activityViewModels()

    private val detailClickListener: ((String)->Unit) = {

    }

    protected val basketIconClickListener: ((ItemModel)->Unit) = { itemModel ->
        if (itemModel.isCartAdded) {
            mainViewModel.setCurrentFragment(FragmentType.Basket)
        } else {
            basketViewModel.setSelectedBasketItem(itemModel)
            showBottomSheet()
        }
    }

    protected fun showBottomSheet() {
        val targetBottomSheet = parentFragmentManager.findFragmentByTag(BasketBottomSheet.TAG)
        if (targetBottomSheet == null) {
            val bottomSheet = BasketBottomSheet()
            bottomSheet.show(parentFragmentManager, BasketBottomSheet.TAG)
        }
    }

}