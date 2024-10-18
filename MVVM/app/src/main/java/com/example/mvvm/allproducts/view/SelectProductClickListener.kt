package com.example.mvvm.allproducts.view

import com.example.mvvm.model.ProductDTO

interface SelectProductClickListener {
   open fun OnSelect(productDTO: ProductDTO)
}