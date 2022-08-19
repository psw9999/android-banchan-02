package com.example.banchan.presentation.productdetail

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.example.banchan.R
import com.example.banchan.databinding.FragmentProductDetailBinding
import com.example.banchan.domain.model.ProductDetailModel
import com.example.banchan.domain.model.ResponseState
import com.example.banchan.presentation.adapter.productdetail.ProductDetailSectionAdapter
import com.example.banchan.presentation.adapter.productdetail.ProductDetailThumbNailAdapter
import com.example.banchan.presentation.adapter.productdetail.ProductInfoAdapter
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.dialog.BasketCheckDialog
import com.example.banchan.presentation.main.BasketViewModel
import com.example.banchan.presentation.main.FragmentType
import com.example.banchan.presentation.main.MainViewModel
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

    private val mainViewModel: MainViewModel by activityViewModels()
    private val basketViewModel: BasketViewModel by activityViewModels()
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
                    productDetailViewModel.productDetail.collectLatest {
                        if (it is ResponseState.Success) {
                            setRecyclerViewData(it.data!!)
                            binding.rvProductDetail.visibility = View.VISIBLE
                            binding.pbDetailLoading.visibility = View.INVISIBLE
                        } else {
                            binding.rvProductDetail.visibility = View.INVISIBLE
                            binding.pbDetailLoading.visibility = View.VISIBLE
                        }
                    }
                }

                launch {
                    basketViewModel.basketFlow.collectLatest { result ->
                        result.onSuccess { binding.abProductDetail.setCartCount(it.size) }
                        result.onFailure { requireContext().toast(getString(R.string.basket_get_error)) }
                    }
                }

                launch {
                    basketViewModel.isInsertSuccess.collectLatest { isInsertSuccess ->
                        if (isInsertSuccess) showDialog()
                        else requireContext().toast(getString(R.string.basket_insert_error))
                    }
                }
            }
        }

        basketViewModel.selectedBasketCount.observe(viewLifecycleOwner) {
            productDetailAdapter.notifyItemChanged(0, it)
        }
    }

    override fun initViews() {
        initRecyclerView()
        binding.abProductDetail.setOnCartClickListener {
            mainViewModel.setCurrentFragment(FragmentType.Basket)
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

}