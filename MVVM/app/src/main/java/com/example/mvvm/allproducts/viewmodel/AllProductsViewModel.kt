package com.example.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mvvm.ProductState
import com.example.mvvm.model.ProductDTO
import com.example.mvvm.model.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AllProductsViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productState = MutableStateFlow<ProductState>(ProductState.Loading)
    val productState: StateFlow<ProductState> = _productState

    fun fetchProducts() {
        viewModelScope.launch {
            _productState.value = ProductState.Loading // Emit Loading state

            try {
                val response = repository.fetchProductsFromApi()
                if (response.isSuccessful) {
                    response.body()?.let { productResponse ->
                        _productState.value = ProductState.Success(productResponse.getProductResponse())
                    } ?: run {
                        _productState.value = ProductState.Error("No products found")
                    }
                } else {
                    _productState.value = ProductState.Error("Error fetching products: ${response.code()}")
                }
            } catch (e: Exception) {
                _productState.value = ProductState.Error(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun insertProductToDb(product: ProductDTO): Long {
        return repository.insertProductToDb(product)
    }
}

class AllProductsViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllProductsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AllProductsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
