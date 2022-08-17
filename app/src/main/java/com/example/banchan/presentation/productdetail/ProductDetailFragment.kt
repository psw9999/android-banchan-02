
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
import com.example.banchan.domain.model.BasketModel
import com.example.banchan.domain.model.ProductDetailModel
import com.example.banchan.domain.model.ResponseState
import com.example.banchan.presentation.adapter.productdetail.*
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.dialog.BasketCheckDialog
import com.example.banchan.presentation.main.BasketViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {

    private val basketViewModel: BasketViewModel by activityViewModels()
    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    private val onMinusClick: (()->Unit) = { basketViewModel.basketCountDecrease() }
    private val onPlusClick: (()->Unit) = { basketViewModel.basketCountIncrease() }
    private val onBasketAddClick: (()->Unit) = {
        addBasket()
        showDialog()
    }

    private val thumbnailAdapter by lazy { ProductDetailThumbNailAdapter() }
    private val productDetailAdapter by lazy { ProductInfoAdapter(onMinusClick, onPlusClick, onBasketAddClick) }
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
                    basketViewModel.basketList.collectLatest {
                        binding.abProductDetail.setCartCount(it.size)
                    }
                }
            }
        }

        basketViewModel.selectedBasketCount.observe(viewLifecycleOwner) {
            productDetailAdapter.notifyItemChanged(0, it)
        }
    }

    override fun onStart() {
        super.onStart()
        productDetailViewModel.getProductDetail(
            basketViewModel.selectedBasketItem.value!!.detailHash,
            basketViewModel.selectedBasketItem.value!!.title
        )
    }

    override fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvProductDetail.adapter = ConcatAdapter(thumbnailAdapter, productDetailAdapter, productDetailSectionAdapter)
    }

    private fun setRecyclerViewData(productDetail: ProductDetailModel) {
        thumbnailAdapter.setThumbImageUrls(productDetail.thumbImages)
        productDetailAdapter.setProductInfo(productDetail, basketViewModel.selectedBasketCount.value!!)
        productDetailSectionAdapter.setSectionList(productDetail.detailSection)
    }

    private fun addBasket() {
        with(basketViewModel) {
            addBasketList(
                BasketModel(
                    count = selectedBasketCount.value!!,
                    image = selectedBasketItem.value!!.image,
                    price = selectedBasketItem.value!!.originPrice,
                    name = selectedBasketItem.value!!.title,
                    detailHash = selectedBasketItem.value!!.detailHash
                )
            )
        }
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