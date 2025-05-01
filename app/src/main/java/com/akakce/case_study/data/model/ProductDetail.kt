package com.akakce.case_study.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDetail(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: ProductRating
)

data class ProductRating(
    val rate: Double,
    val count: Int
)
