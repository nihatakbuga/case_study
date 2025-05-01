package com.akakce.case_study.module

import android.content.Context
import com.akakce.case_study.data.repository.ProductRepository
import com.akakce.case_study.network.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideProductRepository(
        productService: ProductService,
        @ApplicationContext context: Context
    ): ProductRepository {
        return ProductRepository(productService, context)
    }
}