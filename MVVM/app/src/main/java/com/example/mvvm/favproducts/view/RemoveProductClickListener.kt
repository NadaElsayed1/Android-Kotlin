package com.example.mvvm.favproducts.view
import com.example.mvvm.model.ProductDTO

interface RemoveProductClickListener {
    open fun OnRemove(productDTO: ProductDTO)
}