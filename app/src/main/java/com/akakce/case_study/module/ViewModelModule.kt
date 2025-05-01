package com.akakce.case_study.module

import com.akakce.case_study.data.repository.ProductRepository
import com.akakce.case_study.ui.viewmodel.MainViewModel
import com.akakce.case_study.ui.viewmodel.ProductDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideMainViewModel(repository: ProductRepository): MainViewModel {
        return MainViewModel(repository)
    }


    @Provides
    fun provideProductDetailViewModel(repository: ProductRepository): ProductDetailViewModel {
        return ProductDetailViewModel(repository)
    }

}
