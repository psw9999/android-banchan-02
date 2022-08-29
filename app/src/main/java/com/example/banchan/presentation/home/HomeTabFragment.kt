package com.example.banchan.presentation.home

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.commit
import com.example.banchan.R
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.basket.BasketFragment
import com.example.banchan.presentation.dialog.BasketBottomSheet
import com.example.banchan.presentation.productdetail.ProductDetailFragment

abstract class HomeTabFragment<DB : ViewDataBinding>(@LayoutRes val layout: Int) :
    BaseFragment<DB>(layout) {

    protected val detailClickListener: ((ItemModel) -> Unit) = { itemModel ->
        navigateToDetail(itemModel)
    }

    protected val basketIconClickListener: ((ItemModel) -> Unit) = { itemModel ->
        if (itemModel.isCartAdded) {
            navigateToBasket()
        } else {
            showBottomSheet(itemModel)
        }
    }

    private fun navigateToBasket() {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.layout_main_container, BasketFragment(), BasketFragment.TAG)
            addToBackStack(BasketFragment.TAG)
        }
    }

    private fun navigateToDetail(itemModel: ItemModel) {
        requireActivity().supportFragmentManager.commit {
            replace(
                R.id.layout_main_container,
                ProductDetailFragment.newInstance(hash = itemModel.detailHash, name = itemModel.title),
                ProductDetailFragment.TAG
            )
            addToBackStack(ProductDetailFragment.TAG)
        }
    }

    protected fun showBottomSheet(itemModel: ItemModel) {
        val targetBottomSheet = parentFragmentManager.findFragmentByTag(BasketBottomSheet.TAG)
        if (targetBottomSheet == null) {
            val bottomSheet = BasketBottomSheet.newInstance(itemModel)
            bottomSheet.show(parentFragmentManager, BasketBottomSheet.TAG)
        }
    }

    companion object {
        const val TAG = "home_tab"
    }
}