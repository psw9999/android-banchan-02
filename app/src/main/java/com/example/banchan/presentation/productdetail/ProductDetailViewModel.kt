package com.example.banchan.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.banchan.domain.model.ProductDetailModel
import com.example.banchan.domain.usecase.detail.GetProductDetailUseCase
import com.example.banchan.domain.usecase.recently.SaveRecentProduct
import com.example.banchan.presentation.UiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductDetailViewModel @AssistedInject constructor(
    @Assisted("hash") private val hash: String,
    @Assisted("name") private val name: String,
    private val productDetailUseCase: GetProductDetailUseCase,
    private val saveRecentProduct: SaveRecentProduct
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<ProductDetailModel>>(UiState.Init)
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { return@update UiState.Loading }
            productDetailUseCase(hash)
                .onSuccess { result ->
                    val detailModel = result.toDetailModel(name)
                    saveRecentProduct(hash, name)
                    _uiState.emit(UiState.Success(detailModel))
                }
                .onFailure {
                    _uiState.emit(UiState.Error(Throwable("Network Error")))
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