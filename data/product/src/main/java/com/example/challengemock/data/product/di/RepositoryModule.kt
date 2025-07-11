package com.example.challengemock.data.product.di

import com.example.challengemock.data.product.repository.ProductRepositoryImpl
import com.example.challengemock.domain.product.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductRepository(repository: ProductRepositoryImpl): ProductRepository

}