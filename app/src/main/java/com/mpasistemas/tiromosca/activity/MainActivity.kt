package com.mpasistemas.tiromosca.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mpasistemas.tiromosca.databinding.ActivityMainBinding
import com.mpasistemas.tiromosca.modelo.Usuario

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
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
            binding.btnLogin.text = "sair"
        } else {
            binding.btnLogin.text = "login"
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
            intent.putExtra("usuario",usuario)
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
                autenticacao.signOut()
                binding.btnLogin.text = "login"
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
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
                    Toast.makeText(this, "Bem vindo ${this.usuario.nome}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}