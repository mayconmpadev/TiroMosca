package com.mpasistemas.tiromosca.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.databinding.ActivityTorneioBinding

class TorneioActivity : AppCompatActivity() {
    lateinit var binding: ActivityTorneioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityTorneioBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}