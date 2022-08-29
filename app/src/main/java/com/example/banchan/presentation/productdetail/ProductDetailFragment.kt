package com.example.banchan.presentation.productdetail

import android.os.Bundle
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
import com.example.banchan.presentation.main.AppBarViewModel
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
    private val appBarViewModel: AppBarViewModel by activityViewModels()
    private val productDetailViewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModel.provideFactory(
            assistedFactory = factory,
            hash = arguments?.getString(HASH, "") ?: "",
            name = arguments?.getString(NAME, "") ?: ""
        )
    }

    private val onMinusClick: (() -> Unit) = { productDetailViewModel.productCountDecrease() }
    private val onPlusClick: (() -> Unit) = { productDetailViewModel.productCountIncrease() }
    private val onBasketAddClick: (() -> Unit) =
        { productDetailViewModel.insertSelectedBasketItem() }
    private val onThumbNailChange: ((Int) -> Unit) =
        { productDetailViewModel.setSelectedImagePosition(it) }

    private lateinit var thumbnailAdapter: ProductDetailThumbNailAdapter
    private lateinit var productDetailAdapter: ProductInfoAdapter
    private lateinit var productDetailSectionAdapter: ProductDetailSectionAdapter

    override fun initViews() {
        binding.viewModel = productDetailViewModel
        initRecyclerView()
        initAppBar()

        binding.layoutErrorBest.btnHomeErrorReload.setOnClickListener {
            productDetailViewModel.refresh()
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    productDetailViewModel.uiState.collectLatest {
                        if (it is UiState.Success) setRecyclerViewData(it.item)
                    }
                }

                launch {
                    appBarViewModel.basketCount.collectLatest {
                        binding.abProductDetail.setCartCount(it)
                    }
                }

                launch {
                    appBarViewModel.isOrderingStateFlow.collect { isAllOrderSuccess ->
                        binding.abProductDetail.setIsShipping(!isAllOrderSuccess)
                    }
                }

                launch {
                    productDetailViewModel.isInsertSuccess.collectLatest { isInsertSuccess ->
                        if (isInsertSuccess) showDialog()
                        else requireContext().toast(getString(R.string.basket_insert_error))
                    }
                }

                launch {
                    productDetailViewModel.productCount.collect {
                        productDetailAdapter.notifyItemChanged(0, it)
                    }
                }
            }
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
        thumbnailAdapter = ProductDetailThumbNailAdapter(
            onThumbNailChange,
            productDetailViewModel.selectedImagePosition
        )
        productDetailAdapter = ProductInfoAdapter(
            onMinusClick,
            onPlusClick,
            onBasketAddClick
        )
        productDetailSectionAdapter = ProductDetailSectionAdapter()

        binding.rvProductDetail.adapter =
            ConcatAdapter(thumbnailAdapter, productDetailAdapter, productDetailSectionAdapter)
    }

    private fun setRecyclerViewData(productDetail: ProductDetailModel) {
        thumbnailAdapter.setThumbImageUrls(productDetail.thumbImages)
        productDetailAdapter.setProductInfo(
            productDetail,
            productDetailViewModel.productCount.value
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
        const val NAME = "name"
        const val HASH = "hash"
        fun newInstance(hash: String, name: String) = ProductDetailFragment().apply {
            arguments = Bundle().apply {
                putString(HASH, hash)
                putString(NAME, name)
            }
        }
    }
}