package com.example.mvvm.allproducts.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.db.ProductDAO
import com.example.mvvm.db.ProductDataBase
import com.example.mvvm.favproducts.view.FavProductsActivity
import com.example.mvvm.model.ProductRepository
import com.example.mvvm.model.ProductResponse
import com.example.mvvm.service.API.retrofitService
import com.example.mvvm.model.ProductDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class AllProductsActivity : AppCompatActivity(), SelectProductClickListener {

      lateinit var repo: ProductRepository
      lateinit var rvproduct: RecyclerView
      lateinit var productAdapter: ListProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_products)

        rvproduct = findViewById(R.id.rvproducts)

        productAdapter = ListProductAdapter(this, this)

        rvproduct.adapter = productAdapter
        rvproduct.layoutManager = LinearLayoutManager(this)


        lifecycleScope.launch(Dispatchers.IO) {
            val response: Response<ProductResponse> = retrofitService.getProducts()
            val products = response.body()?.getProductResponse()
            withContext(Dispatchers.Main)
            {
                products?.let {
                    productAdapter.submitList(it)
                }
            }
        }
    }


    override fun OnSelect(productDTO: ProductDTO) {
        val myProductDAO: ProductDAO by lazy {
            ProductDataBase.getInstatnce(this).getProductDao()
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val id = myProductDAO.insert(productDTO)
            withContext(Dispatchers.Main)
            {
                if (id > 0)
                {
                    Toast.makeText(this@AllProductsActivity, "Item ${productDTO.title} added to favorites", Toast.LENGTH_SHORT).show()
                }
                else{
                    Log.e("AllProductActivity", "Failed to add item to favorites")
                }
            }
        }
    }
}