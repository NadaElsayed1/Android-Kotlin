package com.example.mvvm.model

class ProductResponse {
    var products: List<ProductDTO>? = null

    fun ProductResponse(products: List<ProductDTO>?) {
        this.products = products
    }

    fun getProductResponse(): List<ProductDTO>? {
        return products
    }
}