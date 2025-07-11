package com.example.challengemock.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "rating")
    val rating: Float,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "discountPercentage")
    val discount: Double,

    @ColumnInfo(name = "stock")
    val stock: Int,

    @ColumnInfo(name = "image")
    val image: String?,
)