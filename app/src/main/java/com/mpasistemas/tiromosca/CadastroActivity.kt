package com.mpasistemas.tiromosca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mpasistemas.tiromosca.databinding.ActivityCadastroBinding

class CadastroActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }

    private val autenticacao by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(binding.root)

        binding.btnSalvar.setOnClickListener() {
            validarCampos()

        }
    }

    fun verificar(editText: EditText): Boolean {
        if ( !editText.text.toString().isNotEmpty()){
            exibirMensagem("o campo $editText.hint.toString() nao ")
        }

        return editText.text.toString().isNotEmpty()
    }

    fun validarCampos() {
        if (verificar(binding.editNome) && verificar(binding.editEmail) && verificar(binding.editSenha1) && verificar(
                binding.editSenha2
            )
        ) {
            exibirMensagem("ok")

        }
    }

    private fun exibirMensagem(texto: String) {
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show()
    }

}