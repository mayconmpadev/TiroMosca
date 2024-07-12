package com.mpasistemas.tiromosca.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mpasistemas.tiromosca.databinding.ActivityCadastroBinding
import com.mpasistemas.tiromosca.modelo.Usuario
import com.mpasistemas.tiromosca.util.Timestamp

class CadastroActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }

    private val autenticacao by lazy {
        FirebaseAuth.getInstance()

    }

    private val database by lazy {
        FirebaseFirestore.getInstance()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(binding.root)

        binding.btnSalvar.setOnClickListener() {
            validarCampos()

        }
    }

    fun verificar(editText: EditText): Boolean {
        if (!editText.text.toString().isNotEmpty()) {
            exibirMensagem("o campo $editText.hint.toString() nao ")
        }

        return editText.text.toString().isNotEmpty()
    }

    fun validarCampos() {
        if (verificar(binding.editNome) && verificar(binding.editEmail) && verificar(binding.editSenha1) && verificar(
                binding.editSenha2
            )
        ) {
            autenticacao.createUserWithEmailAndPassword(
                binding.editEmail.text.toString(),
                binding.editSenha1.text.toString()
            ).addOnSuccessListener { user ->

                if (user != null) {
                    salvarDados(user.user!!.uid)
                }


            }


        }
    }

    private fun exibirMensagem(texto: String) {
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show()
    }

    fun salvarDados(id: String) {
        val usuario = Usuario()
        usuario.id = id
        usuario.nome = binding.editNome.text.toString()
        usuario.data = Timestamp.getUnixTimestamp().toString()
        usuario.status = "online"
        database.collection("usuarios").document(id).set(usuario)
            .addOnSuccessListener {
                exibirMensagem("salvo com sucesso")
                autenticacao.signOut()
            }
            .addOnFailureListener { e -> exibirMensagem("erro: ${e.message}") }
    }

}