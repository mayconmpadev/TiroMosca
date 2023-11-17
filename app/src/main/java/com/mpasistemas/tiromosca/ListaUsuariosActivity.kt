package com.mpasistemas.tiromosca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mpasistemas.tiromosca.databinding.ActivityListaUsuariosBinding

class ListaUsuariosActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListaUsuariosBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}