package com.mpasistemas.tiromosca.activity

import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService.Ranking
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.adapter.RankingAdapter
import com.mpasistemas.tiromosca.adapter.UsuariosAdapter
import com.mpasistemas.tiromosca.databinding.ActivityRankingBinding
import com.mpasistemas.tiromosca.databinding.ActivityTorneioBinding
import com.mpasistemas.tiromosca.modelo.Torneio
import com.mpasistemas.tiromosca.modelo.Usuario

class RankingActivity : AppCompatActivity() {
    var rankingAdapter: RankingAdapter? = null
    var listenerRegistration: ListenerRegistration? = null
    lateinit var rankingList: MutableList<Torneio>
    var usuario: Usuario? = null
    private val binding by lazy {
        ActivityRankingBinding.inflate(layoutInflater)
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
        recuperarIntent()
        configRVUsuarios()
        monitoraraUsuario()

        binding.btnInicio.setOnClickListener() {
            if (autenticacao.currentUser != null) {
                val intent = Intent(this, TorneioActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Para jogar no Torneio faça Login", Toast.LENGTH_LONG).show()
            }

        }

    }

    fun recuperarIntent() {

        usuario = intent.getParcelableExtra<Usuario>("usuario")
    }

    fun configRVUsuarios() {
        binding.rvRanking.layoutManager = LinearLayoutManager(this)
        binding.rvRanking.setHasFixedSize(true)
        rankingAdapter = RankingAdapter(baseContext)
        binding.rvRanking.adapter = rankingAdapter
    }

    fun monitoraraUsuario() {
        val referencia = bancoFirestore.collection("torneio")
        listenerRegistration = referencia.orderBy("pontos", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, erro ->
                if (erro != null) {
                    // Tratar erro
                    Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (snapshots != null) {

                    rankingList = mutableListOf()
                    val newDocumentList = snapshots.documents
                    newDocumentList.forEach { dc ->
                        val ranking = dc.toObject(Torneio::class.java)
                        if (ranking != null) {

                            rankingList.add(ranking)
                            ranking.posicao = rankingList.size

                        }
                    }
                    rankingAdapter?.updateDocuments(rankingList)
                }
            }
    }
}