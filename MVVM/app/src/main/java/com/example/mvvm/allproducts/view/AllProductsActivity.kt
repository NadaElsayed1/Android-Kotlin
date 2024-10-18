package com.example.mvvm.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.viewmodel.AllProductsViewModel
import com.example.mvvm.viewmodel.AllProductsViewModelFactory
import com.example.mvvm.db.ProductDataBase
import com.example.mvvm.model.ProductDTO
import com.example.mvvm.model.ProductRepository
import com.example.mvvm.db.API.retrofitService
import kotlinx.coroutines.launch

class AllProductsActivity : AppCompatActivity(), SelectProductClickListener {

    private val viewModel: AllProductsViewModel by viewModels {
        AllProductsViewModelFactory(ProductRepository(retrofitService, ProductDataBase.getInstatnce(this).getProductDao()))
    }
    lateinit var productAdapter: ListProductAdapter
    private lateinit var rvProduct: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_products)

        rvProduct = findViewById(R.id.rvproducts)

        productAdapter = ListProductAdapter(this, this)
        rvProduct.adapter = productAdapter
        rvProduct.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            viewModel.products.collect { productList ->
                productAdapter.submitList(productList)
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collect { error ->
                error?.let {
                    Toast.makeText(this@AllProductsActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.fetchProducts()
    }

    override fun OnSelect(productDTO: ProductDTO) {
        lifecycleScope.launch {
            val id = viewModel.insertProductToDb(productDTO)
            if (id > 0) {
                Toast.makeText(this@AllProductsActivity, "Item ${productDTO.title} added to favorites", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("AllProductActivity", "Failed to add item to favorites")
            }
        }
    }
}
