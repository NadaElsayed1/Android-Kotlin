package com.example.mvvm

import com.example.mvvm.model.ProductDTO

sealed class ProductState {
    object Loading : ProductState()
    data class Success(val products: List<ProductDTO>) : ProductState()
    data class Error(val message: String) : ProductState()
}