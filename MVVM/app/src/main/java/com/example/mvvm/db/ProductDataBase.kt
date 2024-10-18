package com.example.mvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvm.model.ProductDTO

@Database(entities = arrayOf(ProductDTO::class), version = 1)
abstract class ProductDataBase : RoomDatabase(){
    public abstract fun getProductDao(): ProductDAO
    companion object{
        @Volatile
        private var INSTANCE: ProductDataBase? = null

        fun getInstatnce (ctx:Context): ProductDataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, ProductDataBase::class.java, "product_database")
                    .build()
                INSTANCE = instance
                instance }
        }
    }
}
