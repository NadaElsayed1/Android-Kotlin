package com.example.mvvm.favproducts.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.db.ProductDAO
import com.example.mvvm.db.ProductDataBase
import com.example.mvvm.favproducts.viewmodel.MyViewModel
import com.example.mvvm.favproducts.viewmodel.MyViewModelFactory
import com.example.mvvm.model.ProductDTO
import com.example.mvvm.model.ProductRepository
import com.example.mvvm.service.API
import com.example.mvvm.service.ApiService
import kotlinx.coroutines.launch

class FavProductsActivity : AppCompatActivity(), RemoveProductClickListener {

    private lateinit var rvFavProducts: RecyclerView
    private lateinit var productAdapter: ListFavProductAdapter
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_products)


//        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        val myProductDAO: ProductDAO by lazy {
            ProductDataBase.getInstatnce(this).getProductDao()
        }
        val apiService: ApiService = API.retrofitService

        val repository = ProductRepository(apiService, myProductDAO)
        val factory = MyViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)

        rvFavProducts = findViewById(R.id.rvFavProducts)
        productAdapter = ListFavProductAdapter(this, this)
        rvFavProducts.adapter = productAdapter
        rvFavProducts.layoutManager = LinearLayoutManager(this)

        observeProducts()
    }

    private fun observeProducts(){
        lifecycleScope.launch {
            viewModel.productList.collect{ products ->
                productAdapter.submitList(products)

            }
        }
    }

    override fun OnRemove(productDTO: ProductDTO) {
        viewModel.deleteProduct(productDTO)
        Toast.makeText(this, "Item ${productDTO.title} removed from favorites", Toast.LENGTH_SHORT).show()
    }
}
