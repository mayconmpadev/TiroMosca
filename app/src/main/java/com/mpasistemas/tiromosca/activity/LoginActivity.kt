package com.mpasistemas.tiromosca.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.br.jafapps.bdfirestore.util.DialogProgress

import com.google.firebase.auth.FirebaseAuth
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val autenticacao by lazy {
        FirebaseAuth.getInstance()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEntrar.setOnClickListener {
            if (binding.editEmail.text.isNotEmpty() && binding.editSenha.text.isNotEmpty()) {
                logar(binding.editEmail.text, binding.editSenha.text)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }

        }

        binding.editSenha.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {

                if (event.rawX >= (binding.editSenha.right - binding.editSenha.compoundDrawables[2].bounds.width())) {

                    val visivel = ContextCompat.getDrawable(this, R.drawable.baseline_visibility_24)
                    val oculto =
                        ContextCompat.getDrawable(this, R.drawable.baseline_visibility_off_24)
                    if (binding.editSenha.inputType == 129) {
                        binding.editSenha.inputType = 145
                        binding.editSenha.setCompoundDrawablesWithIntrinsicBounds(
                            binding.editSenha.compoundDrawables[0], // drawableLeft
                            binding.editSenha.compoundDrawables[1], // drawableTop
                            oculto,                 // drawableRight
                            binding.editSenha.compoundDrawables[3]  // drawableBottom
                        )

                    } else {
                        binding.editSenha.inputType = 129
                        binding.editSenha.setCompoundDrawablesWithIntrinsicBounds(
                            binding.editSenha.compoundDrawables[0], // drawableLeft
                            binding.editSenha.compoundDrawables[1], // drawableTop
                            visivel,                 // drawableRight
                            binding.editSenha.compoundDrawables[3]  // drawableBottom
                        )
                    }
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }

        binding.llCadastroUsuario.setOnClickListener() {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        // Adicionar callback para o botão de voltar
        val intent2 = Intent(this, MainActivity::class.java)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startActivity(intent2)
                finish()
            }
        })
    }

    private fun shouldHandleBackPress(): Boolean {
        // Sua lógica para determinar se deve manusear o botão de volta ou não
        return true
    }




    fun logar(email: Editable, senha: Editable) {
        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")
        autenticacao.signInWithEmailAndPassword(
            email.toString(),
            senha.toString()
        ).addOnSuccessListener { sucesso ->
            dialogProgress.dismiss()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", sucesso.user?.email)
            startActivity(intent)
            finish()
        }.addOnFailureListener { exception ->
            dialogProgress.dismiss()
            Toast.makeText(this, exception.message, LENGTH_LONG).show()
        }

    }


}