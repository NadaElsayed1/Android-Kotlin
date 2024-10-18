package com.example.mvvm.model

import com.example.mvvm.db.ProductDAO
import com.example.mvvm.db.ApiService
import com.example.mvvm.ProductState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

class ProductRepository(
    private val apiService: ApiService,
    private val productDAO: ProductDAO
) {

    suspend fun getAllProductsFromDb(): Flow<List<ProductDTO>> {
        return productDAO.getAll()
    }

    suspend fun fetchProductsFromApi(): Response<ProductResponse> {
        return apiService.getProducts()
    }

    suspend fun insertProductToDb(product: ProductDTO): Long {
        return productDAO.insert(product)
    }

    suspend fun deleteProductFromDb(product: ProductDTO): Int {
        return productDAO.delete(product)
    }
}
