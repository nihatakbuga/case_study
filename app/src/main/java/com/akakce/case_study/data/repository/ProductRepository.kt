package com.akakce.case_study.data.repository

import android.content.Context
import com.akakce.case_study.data.model.Product
import com.akakce.case_study.data.model.ProductDetail
import com.akakce.case_study.network.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService,
    context: Context
) : BaseRepository(context) {

    suspend fun getProducts(): Result<List<Product>> = apiCall {
        productService.getProducts()
    }

    suspend fun getProductDetails(productId: Int): Result<ProductDetail> = apiCall {
        productService.getProductDetails(productId)
    }

    suspend fun getLimitedProducts(limit: Int): Result<List<Product>> = apiCall {
        productService.getProducts(limit)
    }

}
