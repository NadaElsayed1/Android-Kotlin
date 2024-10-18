package com.example.mvvm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm.view.AllProductsActivity
import com.example.mvvm.favproducts.view.FavProductsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnAllProducts: Button = findViewById(R.id.AllProducts)
        val btnFavoriteProducts: Button = findViewById(R.id.FavoriteProducts)
        val btnExit: Button = findViewById(R.id.Exit)

        btnAllProducts.setOnClickListener {
            val intent = Intent(this, AllProductsActivity::class.java)
            startActivity(intent)
        }

        btnFavoriteProducts.setOnClickListener {
            val intent = Intent(this, FavProductsActivity::class.java)
            startActivity(intent)
        }

        btnExit.setOnClickListener {
            finishAffinity()
        }
    }
}