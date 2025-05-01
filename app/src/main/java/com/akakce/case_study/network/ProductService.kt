package com.akakce.case_study.network


import com.akakce.case_study.data.model.Product
import com.akakce.case_study.data.model.ProductDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int): List<Product>


    @GET("products/{id}")
    suspend fun getProductDetails(@Path("id") productId: Int): ProductDetail

}
