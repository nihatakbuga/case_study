package com.akakce.case_study.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akakce.case_study.data.model.ProductDetail
import com.akakce.case_study.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productDetail = MutableLiveData<Result<ProductDetail>>()
    val productDetail = _productDetail

    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading

    fun fetchProductDetail(productId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _productDetail.value = repository.getProductDetails(productId)
            _loading.value = false
        }
    }
}
