package com.example.banchan.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.banchan.domain.model.ProductDetailModel
import com.example.banchan.domain.model.ResponseState
import com.example.banchan.domain.usecase.detail.GetProductDetailUseCase
import com.example.banchan.domain.usecase.recently.SaveRecentProduct
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel @AssistedInject constructor(
    @Assisted("hash") private val hash: String,
    @Assisted("name") private val name: String,
    private val productDetailUseCase: GetProductDetailUseCase,
    private val saveRecentProduct: SaveRecentProduct
) : ViewModel() {

    private val _productDetail =
        MutableStateFlow<ResponseState<ProductDetailModel>>(ResponseState.Loading())
    val productDetail: StateFlow<ResponseState<ProductDetailModel>> = _productDetail

    init {
        viewModelScope.launch {
            productDetailUseCase(hash).let { result ->
                if (result.isSuccess) {
                    val detailModel = result.getOrNull()?.toDetailModel(name)
                    detailModel?.let {
                        saveRecentProduct(hash, name)
                        _productDetail.emit(ResponseState.Success(detailModel))
                    }
                } else if (result.isFailure) {
                    _productDetail.emit(ResponseState.Error(result.exceptionOrNull()))
                }
            }
        }
    }

    @AssistedFactory
    interface HashAssistedFactory {
        fun create(
            @Assisted("hash") hash: String,
            @Assisted("name") name: String
        ): ProductDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: HashAssistedFactory,
            hash: String,
            name: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(hash, name) as T
            }
        }
    }
}