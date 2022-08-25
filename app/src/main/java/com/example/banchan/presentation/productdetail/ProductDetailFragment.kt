package com.example.banchan.presentation.productdetail

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.example.banchan.R
import com.example.banchan.databinding.FragmentProductDetailBinding
import com.example.banchan.domain.model.ProductDetailModel
import com.example.banchan.presentation.UiState
import com.example.banchan.presentation.adapter.productdetail.ProductDetailSectionAdapter
import com.example.banchan.presentation.adapter.productdetail.ProductDetailThumbNailAdapter
import com.example.banchan.presentation.adapter.productdetail.ProductInfoAdapter
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.basket.BasketFragment
import com.example.banchan.presentation.dialog.BasketCheckDialog
import com.example.banchan.presentation.home.OrderStateViewModel
import com.example.banchan.presentation.main.BasketViewModel
import com.example.banchan.presentation.orderlist.OrderListFragment
import com.example.banchan.util.ext.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {
    @Inject
    lateinit var factory: ProductDetailViewModel.HashAssistedFactory
    private val basketViewModel: BasketViewModel by activityViewModels()
    private val orderStateViewModel: OrderStateViewModel by activityViewModels()
    private val productDetailViewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModel.provideFactory(
            assistedFactory = factory,
            hash = basketViewModel.selectedBasketItem.value?.detailHash ?: "",
            name = basketViewModel.selectedBasketItem.value?.title ?: ""
        )
    }


    private val onMinusClick: (() -> Unit) = { basketViewModel.basketCountDecrease() }
    private val onPlusClick: (() -> Unit) = { basketViewModel.basketCountIncrease() }
    private val onBasketAddClick: (() -> Unit) = { basketViewModel.insertSelectedBasketItem() }

    private val thumbnailAdapter by lazy { ProductDetailThumbNailAdapter() }
    private val productDetailAdapter by lazy {
        ProductInfoAdapter(
            onMinusClick,
            onPlusClick,
            onBasketAddClick
        )
    }
    private val productDetailSectionAdapter by lazy { ProductDetailSectionAdapter() }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    productDetailViewModel.uiState.collectLatest {
                        if (it is UiState.Success) setRecyclerViewData(it.item)
                    }
                }

                launch {
                    basketViewModel.basketFlow.collectLatest { result ->
                        result.onSuccess { binding.abProductDetail.setCartCount(it.size) }
                        result.onFailure { requireContext().toast(getString(R.string.basket_get_error)) }
                    }
                }

                launch {
                    orderStateViewModel.isOrderingStateFlow.collect { isAllOrderSuccess ->
                        binding.abProductDetail.setIsShipping(!isAllOrderSuccess)
                    }
                }

                launch {
                    basketViewModel.isInsertSuccess.collectLatest { isInsertSuccess ->
                        if (isInsertSuccess) showDialog()
                        else requireContext().toast(getString(R.string.basket_insert_error))
                    }
                }

                launch {
                    orderStateViewModel.isOrderingStateFlow.collect { isAllOrderSuccess ->
                        binding.abProductDetail.setIsShipping(!isAllOrderSuccess)
                    }
                }
            }
        }

        basketViewModel.selectedBasketCount.observe(viewLifecycleOwner) {
            productDetailAdapter.notifyItemChanged(0, it)
        }
    }

    override fun initViews() {
        binding.viewModel = productDetailViewModel
        initRecyclerView()
        initAppBar()

        binding.layoutErrorBest.btnHomeErrorReload.setOnClickListener {
            productDetailViewModel.refresh()
        }
    }

    private fun initAppBar() {
        binding.abProductDetail.apply {
            setOnCartClickListener {
                navigateToBasket()
            }
            setOnProfileClickListener {
                navigateToProfile()
            }
        }
    }

    private fun navigateToBasket() {
        parentFragmentManager.commit {
            replace(R.id.layout_main_container, BasketFragment(), BasketFragment.TAG)
            addToBackStack(BasketFragment.TAG)
        }
    }

    private fun navigateToProfile() {
        parentFragmentManager.commit {
            replace(R.id.layout_main_container, OrderListFragment(), OrderListFragment.TAG)
            addToBackStack(OrderListFragment.TAG)
        }
    }

    private fun initRecyclerView() {
        binding.rvProductDetail.adapter =
            ConcatAdapter(thumbnailAdapter, productDetailAdapter, productDetailSectionAdapter)
    }

    private fun setRecyclerViewData(productDetail: ProductDetailModel) {
        thumbnailAdapter.setThumbImageUrls(productDetail.thumbImages)
        productDetailAdapter.setProductInfo(
            productDetail,
            basketViewModel.selectedBasketCount.value!!
        )
        productDetailSectionAdapter.setSectionList(productDetail.detailSection)
    }

    private fun showDialog() {
        val targetDialog = parentFragmentManager.findFragmentByTag(BasketCheckDialog.TAG)
        if (targetDialog == null) {
            val checkDialog = BasketCheckDialog()
            checkDialog.isCancelable = false
            checkDialog.show(parentFragmentManager, BasketCheckDialog.TAG)
        }
    }

    companion object {
        const val TAG = "product_detail"
    }
}