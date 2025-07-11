package com.example.challengemock.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.challengemock.data.database.dao.ProductDao
import com.example.challengemock.data.database.dao.ProductRemoteKeyDao
import com.example.challengemock.data.database.entity.ProductEntity
import com.example.challengemock.data.database.entity.ProductRemoteKeyEntity

@Database(
    entities = [
        ProductEntity::class,
        ProductRemoteKeyEntity::class
   ],
    exportSchema = false,
    version = 1,
)
abstract class ChallengeMockDatabase: RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getProductRemoteKeyDao(): ProductRemoteKeyDao
}