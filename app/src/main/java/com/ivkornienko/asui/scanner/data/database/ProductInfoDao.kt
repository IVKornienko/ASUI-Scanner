package com.ivkornienko.asui.scanner.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductInfoDao {

    @Query("SELECT * FROM history_scan ORDER BY id DESC")
    fun getListProductInfo(): LiveData<List<ProductInfoDbModel>>

    @Query("SELECT * FROM history_scan WHERE id == :id LIMIT 1")
    suspend fun getProductInfo(id: Long): ProductInfoDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductInfo(productInfo: ProductInfoDbModel): Long

    @Query("DELETE FROM history_scan")
    suspend fun clearHistoryScan()
}