package com.example.mvvm.favproducts.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mvvm.model.ProductRepository
import com.example.mvvm.model.ProductDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyViewModel(private val repository: ProductRepository): ViewModel() {

//    private var repo = ProductRepository
    private var _productList = MutableStateFlow<List<ProductDTO>>(emptyList())
    val productList: StateFlow<List<ProductDTO>> = _productList

    init {
        getLocalProducts()
        Log.i("TAG", ":Create ViewModel ")
    }

    fun deleteProduct(productDTO: ProductDTO){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProductFromDb(productDTO)
            /*to update my product list after deletion*/
            getLocalProducts()
        }
    }

    fun getLocalProducts(){
        viewModelScope.launch(Dispatchers.IO) {
//            _productList.postValue(repository.getAllProductsFromDb())
            repository.getAllProductsFromDb().collect{
                /*update the satate flow with the latest value and using Indicative of that with products instead of it*/
                products ->
                _productList.value = products

            }
        }
    }

}

class MyViewModelFactory(private val repository: ProductRepository): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModel(repository) as T
    }
}