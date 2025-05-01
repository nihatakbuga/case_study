package com.akakce.case_study.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akakce.case_study.data.model.Product
import com.akakce.case_study.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private fun fetchHorizontalProducts() {
        viewModelScope.launch {
            _loading.value = true
            _horizontalProducts.value = repository.getLimitedProducts(5)
            _loading.value = false
        }
    }

    private fun fetchAllProducts() {
        viewModelScope.launch {
            products.value = repository.getProducts()
        }
    }

    init {
        fetchHorizontalProducts()
        fetchAllProducts()
    }
}
