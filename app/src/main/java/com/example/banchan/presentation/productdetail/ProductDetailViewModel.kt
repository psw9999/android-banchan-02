package com.example.banchan.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.recent.RecentlyProduct
import com.example.banchan.domain.model.ProductDetailModel
import com.example.banchan.domain.model.ResponseState
import com.example.banchan.domain.usecase.detail.GetProductDetailUseCase
import com.example.banchan.domain.usecase.recently.InsertRecentlyItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDetailUseCase: GetProductDetailUseCase,
    private val insertRecentlyItemUseCase: InsertRecentlyItemUseCase
) : ViewModel() {

    private val _productDetail = MutableStateFlow<ResponseState<ProductDetailModel>>(ResponseState.Loading())
    val productDetail: StateFlow<ResponseState<ProductDetailModel>> = _productDetail

    fun getProductDetail(hash: String, name: String) {
        viewModelScope.launch {
            productDetailUseCase(hash).let { result ->
                if (result.isSuccess) {
                    val detailModel = result.getOrThrow().toDetailModel(name)
                    _productDetail.emit(ResponseState.Success(detailModel))
                } else if(result.isFailure) {
                    _productDetail.emit(ResponseState.Error(result.exceptionOrNull()))
                }
            }
        }
    }

    fun insertProductDetail(vararg recentlyProduct: RecentlyProduct) {
        viewModelScope.launch {
            insertRecentlyItemUseCase(*recentlyProduct).let { result ->
                result.onFailure {
                    //TODO: 최근 본 상품 저장 실패시 시나리오 정의 필요
                }
            }
        }
    }
}