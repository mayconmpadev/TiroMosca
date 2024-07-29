package com.mpasistemas.tiromosca.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mpasistemas.tiromosca.databinding.ActivityTorneioBinding
import com.mpasistemas.tiromosca.databinding.ItemTorneioBinding
import com.mpasistemas.tiromosca.modelo.Torneio
import com.mpasistemas.tiromosca.modelo.Usuario

class RankingAdapter (  val context: Context) : Adapter<RankingAdapter.MyViewHolder>(){
    private var documentList: MutableList<Torneio> = mutableListOf()
    var i : Int = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemTorneioBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = documentList.size

    fun updateDocuments(newDocumentList: MutableList<Torneio>) {
i = 1
        val diffCallback = object : DiffUtil.Callback() {

            override fun getOldListSize() = documentList.size

            override fun getNewListSize() = newDocumentList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return documentList[oldItemPosition].id == newDocumentList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return documentList[oldItemPosition] == newDocumentList[newItemPosition]
            }
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        documentList = newDocumentList
//Toast.makeText(context, "diffutil",Toast.LENGTH_SHORT).show()
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ranking = documentList[position]
        holder.binding.textPosicao.text = ranking.posicao.toString()
        holder.binding.textCompetidor.text = ranking.nome
        holder.binding.textPontuacao.text = ranking.pontos.toString()
    }
    inner class MyViewHolder(val binding: ItemTorneioBinding) : ViewHolder(binding.root)

}