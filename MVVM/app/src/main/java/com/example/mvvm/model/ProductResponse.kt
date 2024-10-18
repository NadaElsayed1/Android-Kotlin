package com.example.mvvm.model

class ProductResponse {
    private var products: List<ProductDTO>? = null

    fun ProductResponse(products: List<ProductDTO>?) {
        this.products = products
    }

    fun getProductResponse(): List<ProductDTO>? {
        return products
    }

    fun setProducts(products: List<ProductDTO>?) {
        this.products = products
    }

}