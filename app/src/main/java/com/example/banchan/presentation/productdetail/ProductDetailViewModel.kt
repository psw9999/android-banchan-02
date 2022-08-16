package com.example.banchan.presentation.productdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.domain.model.ProductDetailModel
import com.example.banchan.domain.model.ResponseState
import com.example.banchan.domain.usecase.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDetailUseCase: GetProductDetailUseCase
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
                } else {

                }
            }
        }
    }
}