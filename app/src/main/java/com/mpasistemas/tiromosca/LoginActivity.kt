package com.mpasistemas.tiromosca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.core.text.set
import com.mpasistemas.tiromosca.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntrar.setOnClickListener {
            Toast.makeText(
                this,
                Util.numeroAleatorio(),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.textCadastro.setOnClickListener(){
           val intent = Intent(this,CadastroActivity::class.java)
            startActivity(intent)
        }

    }
}