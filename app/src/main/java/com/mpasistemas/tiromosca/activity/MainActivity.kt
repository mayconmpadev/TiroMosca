package com.mpasistemas.tiromosca.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.databinding.ActivityMainBinding
import com.mpasistemas.tiromosca.databinding.DialogPadraoOkCancelarBinding
import com.mpasistemas.tiromosca.modelo.Usuario
import com.mpasistemas.tiromosca.util.Util
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var dialog: AlertDialog? = null
    private val autenticacao by lazy {
        FirebaseAuth.getInstance()

    }

    private val bancoFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    var usuario = Usuario()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (autenticacao.currentUser != null) {
            obterUsuario()
            //  val intent = Intent(this, PraticarActivity::class.java)
            //  startActivity(intent)
            binding.btnLogin.text = "S A I R"
        } else {
            binding.btnLogin.text = "L O G I N"
        }
        binding.llPraticar.setOnClickListener() {
            val intent = Intent(this, PraticarActivity::class.java)
            startActivity(intent)
        }

        binding.llDuelo.setOnClickListener() {
            val intent = Intent(this, ListaUsuariosActivity::class.java)
            startActivity(intent)
        }

        binding.llTorneio.setOnClickListener() {

            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)

        }


        binding.llAjuda.setOnClickListener() {
            val intent = Intent(this, AjudaActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener() {
            if (autenticacao.currentUser != null) {
                //  val intent = Intent(this, PraticarActivity::class.java)
                //  startActivity(intent)
                dialogSair(usuario.nome)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

    }



    fun obterUsuario() {

        val caminho =
            bancoFirestore.collection("usuarios").document(autenticacao.currentUser?.uid.toString())

        caminho.get().addOnSuccessListener { documento ->
            if (documento.exists()) {
                var usuario = documento.toObject(Usuario::class.java)
                if (usuario != null) {
                    this.usuario = usuario
                    Toast.makeText(this, "Bem vindo ${this.usuario.nome}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun dialogSair(nome: String) {

        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)

        val dialogBinding: DialogPadraoOkCancelarBinding = DialogPadraoOkCancelarBinding
            .inflate(LayoutInflater.from(this))
dialogBinding.textTitulo.text = "Logoff"
        Util.textoNegrito(
            "*" + nome + "*" + "! Deseja fazer logoff " + "?",
            dialogBinding.textMsg,
            null
        )

        dialogBinding.btnDireita.setOnClickListener { view ->
            autenticacao.signOut()
            binding.btnLogin.text = "L O G I N"
            dialog!!.dismiss()
        }

        dialogBinding.btnEsquerda.setOnClickListener { view ->

            dialog!!.dismiss()
        }

        builder.setView(dialogBinding.getRoot())
        dialog = builder.create()
        dialog!!.show()

        dialog!!.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.8).toInt(),  // 80% da largura da tela
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}