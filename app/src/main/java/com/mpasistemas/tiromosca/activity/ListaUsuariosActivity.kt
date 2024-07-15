package com.mpasistemas.tiromosca.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.jafapps.bdfirestore.util.DialogProgress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.adapter.UsuariosAdapter
import com.mpasistemas.tiromosca.databinding.ActivityListaUsuariosBinding
import com.mpasistemas.tiromosca.databinding.DialogPadraoOkCancelarBinding
import com.mpasistemas.tiromosca.modelo.Usuario
import com.mpasistemas.tiromosca.util.Util

class ListaUsuariosActivity : AppCompatActivity(), UsuariosAdapter.ClickCategoria {
    private val usuarioList: ArrayList<Usuario> = ArrayList()
    var usuario: Usuario? = null
    private var dialog: AlertDialog? = null
    var usuariosAdapter: UsuariosAdapter? = null
    private val binding by lazy {
        ActivityListaUsuariosBinding.inflate(layoutInflater)
    }

    private val autenticacao by lazy {
        FirebaseAuth.getInstance()
    }

    private val bancoFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //recuperaUsuarios()
        monitoraraUsuario()

    }

    fun configRVUsuarios(usuarioList: List<Usuario>) {
        binding.rvUsuario.layoutManager = LinearLayoutManager(this)
        //binding.rvUsuario.setHasFixedSize(true)
        usuariosAdapter = UsuariosAdapter(baseContext, usuarioList, this)
        binding.rvUsuario.adapter = usuariosAdapter
    }

    fun recuperaUsuarios() {
        usuarioList.clear()
        val referencia = bancoFirestore.collection("usuarios")
        referencia.addSnapshotListener { querySnapshot, error ->

            val listaDocuments = querySnapshot?.documents

            listaDocuments?.forEach { documentSnapshot ->
                val usuario = documentSnapshot.toObject(Usuario::class.java)
                if (usuario != null) {
                    if (usuario.id != FirebaseAuth.getInstance().currentUser?.uid) {
                        usuarioList.add(usuario)
                    }
                }
            }
            binding.rvUsuario.adapter?.notifyDataSetChanged()
        }
    }
    fun monitoraraUsuario(){
        usuarioList.clear()
        Toast.makeText(this,"erro1",Toast.LENGTH_SHORT).show()
        val referencia = bancoFirestore.collection("usuarios")
        val listenerRegistration = referencia.addSnapshotListener { snapshots, e ->
            if (e != null) {
                // Tratar erro
                Toast.makeText(this,"erro",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            if (snapshots != null) {
                for (dc in snapshots.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            // Documento adicionado

                            val usuario = dc.document.toObject(Usuario::class.java)
                            usuarioList.add(usuario)
                            configRVUsuarios(usuarioList)
                            //binding.rvUsuario.adapter?.notifyDataSetChanged()
                            Toast.makeText(this,usuario.nome,Toast.LENGTH_SHORT).show()
                        }
                        DocumentChange.Type.MODIFIED -> {
                            // Documento modificado
                            Toast.makeText(this,"modificado",Toast.LENGTH_SHORT).show()
                            val usuario = dc.document.toObject(Usuario::class.java)
                            for (i in usuarioList.indices) {
                                if (usuarioList.get(i).id.equals(usuario.id)) {
                                    usuarioList.set(i, usuario)
                                }
                            }
                            usuariosAdapter?.notifyDataSetChanged()
                        }
                        DocumentChange.Type.REMOVED -> {
                            // Documento removido
                            Toast.makeText(this,"removido",Toast.LENGTH_SHORT).show()
                            binding.rvUsuario.adapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

// Para parar de ouvir as atualizações, chame o método remove() no ListenerRegistration

    }

    private fun showDialogChamar(usuario: Usuario) {

        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)

        val dialogBinding: DialogPadraoOkCancelarBinding = DialogPadraoOkCancelarBinding
            .inflate(LayoutInflater.from(this))

        Util.textoNegrito(
            "Deseja jogar contra " + "*" + usuario.nome + "*" + "?",
            dialogBinding.textMsg,
            null
        )

        dialogBinding.btnDireita.setOnClickListener { view ->
            if (usuario.status.equals("online")) {


            } else {
                dialog!!.dismiss()
                Toast.makeText(baseContext, "usuario  não disponivel", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBinding.btnDireita.setOnClickListener { view ->
            salvarDados(usuario)
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

    private fun showDialogAceitar(usuario: Usuario) {

        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)

        val dialogBinding: DialogPadraoOkCancelarBinding = DialogPadraoOkCancelarBinding
            .inflate(LayoutInflater.from(this))

        Util.textoNegrito(
            "voce aceita jogar contra " + "*" + usuario.nome + "*" + "?",
            dialogBinding.textMsg,
            null
        )

        dialogBinding.btnDireita.setOnClickListener { view ->
            if (usuario.status.equals("online")) {


            } else {
                dialog!!.dismiss()
                Toast.makeText(baseContext, "usuario  não disponivel", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBinding.btnDireita.setOnClickListener { view ->
            salvarDados(usuario)
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

    fun salvarDados(usuario: Usuario) {

        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")

        val reference = bancoFirestore.collection("usuarios")


        reference.document(usuario.id).update("status", autenticacao.currentUser?.uid.toString())
            .addOnSuccessListener {

                dialogProgress.dismiss()
                Toast.makeText(baseContext, "Sucesso ao gravar dados", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { error ->

            dialogProgress.dismiss()
            Toast.makeText(
                baseContext,
                "Erro ao gravar dados: ${error.message.toString()}",
                Toast.LENGTH_SHORT
            ).show()

        }

    }

    //CLICK EM ITEM DA LISTA
    override fun clickCategoria(usuario: Usuario) {

        showDialogChamar(usuario)
    }

}