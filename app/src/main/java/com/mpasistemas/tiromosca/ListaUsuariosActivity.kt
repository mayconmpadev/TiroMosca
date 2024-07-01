package com.mpasistemas.tiromosca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mpasistemas.tiromosca.adapter.jogadasAdapter
import com.mpasistemas.tiromosca.adapter.usuariosAdapter
import com.mpasistemas.tiromosca.databinding.ActivityListaUsuariosBinding
import com.mpasistemas.tiromosca.modelo.Usuario

class ListaUsuariosActivity : AppCompatActivity() {
    var lista: ArrayList<Usuario> = ArrayList()
    private val binding by lazy {
        ActivityListaUsuariosBinding.inflate(layoutInflater)
    }

    private val bancoFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        recuperaUsuarios()
        binding.rvUsuarios.layoutManager = LinearLayoutManager(this)
        binding.rvUsuarios.adapter = usuariosAdapter()
    }

    fun recuperaUsuarios() {
        val referencia = bancoFirestore.collection("usuarios")
        referencia.addSnapshotListener { querySnapshot, error ->

            val listaDocuments = querySnapshot?.documents

            listaDocuments?.forEach { documentSnapshot ->
                val usuario = documentSnapshot.toObject(Usuario::class.java)
                if (usuario != null) {
                    if (usuario.id != FirebaseAuth.getInstance().currentUser?.uid){
                        lista.add(usuario)
                    }


                }
            }
            binding.rvUsuarios.adapter?.notifyDataSetChanged()
        }

    }


}