package com.example.mvvm.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.mvvm.model.ProductDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {
    @androidx.room.Query("SELECT * FROM products_table")
    fun getAll(): Flow<List<ProductDTO>>
//    suspend fun getAll(): List<ProductDTO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(productDTO: ProductDTO): Long

    @Update
    suspend fun update(productDTO: ProductDTO)

    @Delete
    suspend fun delete(productDTO: ProductDTO):Int

}