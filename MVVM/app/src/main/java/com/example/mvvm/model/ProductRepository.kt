package com.example.mvvm.model

import com.example.mvvm.db.ProductDAO
import com.example.mvvm.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class ProductRepository(
    private val apiService: ApiService,
    private val productDAO: ProductDAO) {

//    suspend fun getAllProductsFromDb(): List<ProductDTO> {
    fun getAllProductsFromDb(): Flow<List<ProductDTO>> {
        return productDAO.getAll()
    }

    suspend fun fetchProductsFromApi(): Flow<Response<ProductResponse>> {
        return flow{
            emit(apiService.getProducts()) }
    }

    suspend fun insertProductToDb(product: ProductDTO): Long {
        return productDAO.insert(product)
    }

    suspend fun updateProductInDb(product: ProductDTO) {
        productDAO.update(product)
    }

    suspend fun deleteProductFromDb(product: ProductDTO): Int {
        return productDAO.delete(product)
    }
}
