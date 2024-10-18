package com.example.mvvm.model

class ProductResponse {
    var products: List<ProductDTO>? = null
    fun getProductResponse(): List<ProductDTO>? {
        return products
    }
}