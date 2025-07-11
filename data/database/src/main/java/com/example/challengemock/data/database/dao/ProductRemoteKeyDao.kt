package com.example.challengemock.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.challengemock.data.database.entity.ProductRemoteKeyEntity

@Dao
interface ProductRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKey: List<ProductRemoteKeyEntity>)

    @Query("Select * From product_remote_key Where product_id = :id")
    suspend fun getByProduct(id: Long): ProductRemoteKeyEntity?

    @Query("Delete From product_remote_key")
    suspend fun clear()

    @Query("Select * From product_remote_key Order By currentPage DESC LIMIT 1")
    suspend fun getLatestPage(): ProductRemoteKeyEntity?
}
