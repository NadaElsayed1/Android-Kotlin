package com.example.mvvm.view

import com.example.mvvm.model.ProductDTO

interface SelectProductClickListener {
   open fun OnSelect(productDTO: ProductDTO)
}