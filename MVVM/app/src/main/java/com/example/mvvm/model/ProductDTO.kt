package com.example.mvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "products_table")
data class ProductDTO (@PrimaryKey var id: Long, val title: String, val description: String, val price: Double, val rating: Double, val brand: String, var thumbnail: String):
    Serializable
/*no need for setters and getters*/