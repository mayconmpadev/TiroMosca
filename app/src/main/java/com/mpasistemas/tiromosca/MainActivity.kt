package com.mpasistemas.tiromosca


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mpasistemas.tiromosca.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llPraticar.setOnClickListener() {
            val intent = Intent(this, PraticarActivity::class.java)
            startActivity(intent)
        }

        binding.llPraticar.setOnClickListener() {
            val intent = Intent(this, PraticarActivity::class.java)
            startActivity(intent)
        }

        binding.llPraticar.setOnClickListener() {
            val intent = Intent(this, PraticarActivity::class.java)
            startActivity(intent)
        }

        binding.llPraticar.setOnClickListener() {
            val intent = Intent(this, PraticarActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}