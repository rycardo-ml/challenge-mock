package com.example.challengemock.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_remote_key")
data class ProductRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "product_id")
    val productID: Long,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)