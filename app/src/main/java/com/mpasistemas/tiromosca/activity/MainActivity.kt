package com.mpasistemas.tiromosca.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mpasistemas.tiromosca.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val autenticacao by lazy {
        FirebaseAuth.getInstance()

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (autenticacao.currentUser != null) {
          //  val intent = Intent(this, PraticarActivity::class.java)
          //  startActivity(intent)
            binding.btnLogin.text = "sair"
        }else{
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

        binding.llPraticar.setOnClickListener() {
            val intent = Intent(this, PraticarActivity::class.java)
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
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

        }

        Toast.makeText(baseContext, autenticacao.currentUser?.uid.toString(),Toast.LENGTH_SHORT).show()
    }
}