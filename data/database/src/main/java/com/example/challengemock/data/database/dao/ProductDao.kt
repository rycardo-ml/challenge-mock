package com.example.challengemock.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.challengemock.data.database.entity.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(products: List<ProductEntity>)

    @Query("Select * From product")
    suspend fun getAll(): List<ProductEntity>

    @Query("Select * From product WHERE id = :id")
    suspend fun getById(id: Long): ProductEntity

    @Query("Delete From product")
    suspend fun clear(): Int
}