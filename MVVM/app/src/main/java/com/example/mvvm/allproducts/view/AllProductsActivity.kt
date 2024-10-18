package com.example.mvvm.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.ProductState
import com.example.mvvm.R
import com.example.mvvm.db.ProductDataBase
import com.example.mvvm.model.ProductDTO
import com.example.mvvm.model.ProductRepository
import com.example.mvvm.db.API.retrofitService
import com.example.mvvm.viewmodel.AllProductsViewModel
import com.example.mvvm.viewmodel.AllProductsViewModelFactory
import kotlinx.coroutines.launch

class AllProductsActivity : AppCompatActivity(), SelectProductClickListener {

    private val viewModel: AllProductsViewModel by viewModels {
        AllProductsViewModelFactory(ProductRepository(retrofitService, ProductDataBase.getInstance(this).getProductDao()))
    }
    private lateinit var productAdapter: ListProductAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_products)

        val rvProduct: RecyclerView = findViewById(R.id.rvproducts)
        progressBar = findViewById(R.id.progressBar) 
        productAdapter = ListProductAdapter(this, this)
        rvProduct.adapter = productAdapter
        rvProduct.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            viewModel.productState.collect { state ->
                when (state) {
                    is ProductState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is ProductState.Success -> {
                        productAdapter.submitList(state.products)
                        progressBar.visibility = View.GONE
                    }
                    is ProductState.Error -> {
                        Toast.makeText(this@AllProductsActivity, state.message, Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }
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
