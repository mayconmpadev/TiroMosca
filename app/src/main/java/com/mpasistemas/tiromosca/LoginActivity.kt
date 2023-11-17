package com.mpasistemas.tiromosca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG

import com.google.firebase.auth.FirebaseAuth
import com.mpasistemas.tiromosca.databinding.ActivityLoginBinding
import com.mpasistemas.tiromosca.util.Util

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val autenticacao by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEntrar.setOnClickListener {
            logar(binding.editEmail.text, binding.editSenha.text)
        }

        binding.textCadastro.setOnClickListener() {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }


    }

    fun logar(email: Editable, senha: Editable) {
        autenticacao.signInWithEmailAndPassword(
            email.toString(),
            senha.toString()
        ).addOnSuccessListener { sucesso ->

            val intent = Intent(this, PraticarActivity::class.java)
            intent.putExtra("email", sucesso.user?.email)
            startActivity(intent)
        }.addOnFailureListener { exception ->
            Toast.makeText(this, exception.message, LENGTH_LONG).show()
        }

    }
}