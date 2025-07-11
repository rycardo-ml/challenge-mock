package com.example.challengemock.data.product.repository

import androidx.room.withTransaction
import com.example.challengemock.data.ChallengeMockDatabase
import com.example.challengemock.data.database.dao.ProductDao
import com.example.challengemock.data.database.dao.ProductRemoteKeyDao
import com.example.challengemock.data.database.entity.ProductRemoteKeyEntity
import com.example.challengemock.data.product.mapper.toEntity
import com.example.challengemock.data.product.mapper.toModel
import com.example.challengemock.data.product.remote.ProductService
import com.example.challengemock.domain.product.model.Product
import com.example.challengemock.domain.product.repository.ProductRepository
import javax.inject.Inject

private const val PRODUCT_PAGE_SIZE = 20

class ProductRepositoryImpl @Inject constructor(
    private val service: ProductService,
    private val database: ChallengeMockDatabase,
    private val productDao: ProductDao,
    private val productRemoteKeyDao: ProductRemoteKeyDao,
): ProductRepository {

    override suspend fun fetch(): List<Product> {
        val lastKeyEntity = productRemoteKeyDao.getLatestPage()

        var page = lastKeyEntity?.nextKey ?: 0
        var endOfPaginationReached = lastKeyEntity != null && lastKeyEntity.nextKey == null

        while (!endOfPaginationReached) {

            val apiResponse = service.fetchLazyProducts(
                skip = page * PRODUCT_PAGE_SIZE,
                limit = PRODUCT_PAGE_SIZE
            )

            val products = apiResponse.products
            endOfPaginationReached = apiResponse.total == (PRODUCT_PAGE_SIZE * page) + products.size

            database.withTransaction {
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1

                val remoteKeys = mutableListOf<ProductRemoteKeyEntity>()
                val productsEntities = products.map {
                    remoteKeys.add(ProductRemoteKeyEntity(productID = it.id, prevKey = prevKey, currentPage = page, nextKey = nextKey))
                    it.toEntity()
                }

                productRemoteKeyDao.insert(remoteKeys)
                productDao.insert(productsEntities)

                page++
            }
        }

        return productDao.getAll().map {
            it.toModel() }
    }

    override suspend fun getById(id: Long): Product {
        return productDao.getById(id).toModel()
    }
}
