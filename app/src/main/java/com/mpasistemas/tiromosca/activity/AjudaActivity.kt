package com.mpasistemas.tiromosca.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.databinding.ActivityAjudaBinding
import com.mpasistemas.tiromosca.databinding.ActivityMainBinding

class AjudaActivity : AppCompatActivity() {
    lateinit var binding: ActivityAjudaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAjudaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPraticar.setOnClickListener(){
            val intent = Intent(this, PraticarActivity::class.java)
            startActivity(intent)
            finish()
        }

        }
    }
