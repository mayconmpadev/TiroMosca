package com.mpasistemas.tiromosca.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.modelo.Jogadas

class jogadasAdapter(
    private val jogadasList: List<Jogadas>
) : Adapter<jogadasAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_jogadas, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = jogadasList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val jogada = jogadasList[position]
        holder.textJogada.text = jogada.jogada
        val lista =
            listOf(holder.imagem1, holder.imagem2, holder.imagem3, holder.imagem4)
        for (i in 0..<jogada.mosca.length) {
            if (jogada.mosca[i] == 'm') {
                lista[i].setImageResource(R.drawable.mosca)
            } else if (jogada.mosca[i] == 't') {
                lista[i].setImageResource(R.drawable.tiro)
            }
        }

    }


    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        val textJogada: TextView = itemView.findViewById(R.id.textJogadah)
        val imagem1: ImageView = itemView.findViewById(R.id.image1)
        val imagem2: ImageView = itemView.findViewById(R.id.image2)
        val imagem3: ImageView = itemView.findViewById(R.id.image3)
        val imagem4: ImageView = itemView.findViewById(R.id.image4)
    }
}