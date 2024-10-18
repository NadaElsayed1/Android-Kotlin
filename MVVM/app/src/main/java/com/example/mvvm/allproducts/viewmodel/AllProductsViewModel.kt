package com.example.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mvvm.model.ProductDTO
import com.example.mvvm.model.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AllProductsViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductDTO>>(emptyList())
    val products: StateFlow<List<ProductDTO>> = _products

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchProducts() {
        viewModelScope.launch {
            repository.fetchProductsFromApi()
                .catch { e ->
                    _errorMessage.value = e.message
                }
                .collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.getProductResponse()?.let {
                            _products.value = it
                        }
                    } else {
                        _errorMessage.value = "Error fetching products"
                    }
                }
        }
    }

    fun loadProductsFromDb() {
        viewModelScope.launch {
            repository.getAllProductsFromDb().collect { products ->
                _products.value = products
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

