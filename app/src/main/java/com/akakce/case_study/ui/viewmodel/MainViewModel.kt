package com.akakce.case_study.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akakce.case_study.data.model.Product
import com.akakce.case_study.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val products = MutableLiveData<Result<List<Product>>>()
    val allProducts = products


    private val _horizontalProducts = MutableLiveData<Result<List<Product>>>()
    val horizontalProducts = _horizontalProducts

    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading


    private fun fetchAllProducts() {
        viewModelScope.launch {
            _loading.value = true
            val allProductsResult = async { repository.getProducts() }
            val horizontalProductsResult = async { repository.getLimitedProducts(5) }
            products.value = allProductsResult.await()
            _horizontalProducts.value = horizontalProductsResult.await()
            _loading.value = false
        }
    }

    init {
        fetchAllProducts()
    }
}
