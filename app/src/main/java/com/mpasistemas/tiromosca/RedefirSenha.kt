package com.mpasistemas.tiromosca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mpasistemas.tiromosca.databinding.ActivityLoginBinding
import com.mpasistemas.tiromosca.databinding.ActivityRedefirSenhaBinding

class RedefirSenha : AppCompatActivity() {
    private lateinit var binding: ActivityRedefirSenhaBinding
    private val autenticacao by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRedefirSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnviar.setOnClickListener() {
            val email = binding.editEmail.text.toString()
            resetPassword(email)
        }
    }

    private fun resetPassword(email: String) {
        autenticacao.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext, "Email de redefinição de senha enviado.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext, "Erro ao enviar email de redefinição de senha.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}