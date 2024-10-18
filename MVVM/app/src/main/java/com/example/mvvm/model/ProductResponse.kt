package com.example.mvvm.model

class ProductResponse {
    lateinit var products: List<ProductDTO>
    fun getProductResponse(): List<ProductDTO> {
        return products
    }
}