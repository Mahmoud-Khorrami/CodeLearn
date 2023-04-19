package com.mahapp1397.codelearn1.database

import androidx.room.*
import com.mahapp1397.codelearn1.models.Sale
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSale(sale: Sale): Long

    @Query("SELECT * FROM sales_table")
    fun index(): Flow<List<Sale>>
}