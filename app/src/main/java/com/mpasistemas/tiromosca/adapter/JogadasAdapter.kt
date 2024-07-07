package com.mpasistemas.tiromosca.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.databinding.ItemJogadasBinding
import com.mpasistemas.tiromosca.modelo.Jogadas

class jogadasAdapter(private val jogadasList: List<Jogadas>) : Adapter<jogadasAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
          val itemView = ItemJogadasBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = jogadasList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val jogada = jogadasList[position]
        holder.binding.textJogadah.text = jogada.jogada
        val lista =
            listOf(holder.binding.image1, holder.binding.image2, holder.binding.image3, holder.binding.image4)
        for (i in 0..<jogada.mosca.length) {
            if (jogada.mosca[i] == 'm') {
                lista[i].setImageResource(R.drawable.mosca)
            } else if (jogada.mosca[i] == 't') {
                lista[i].setImageResource(R.drawable.tiro)
            }
        }

    }


   inner class MyViewHolder(val binding: ItemJogadasBinding) : ViewHolder(binding.root) {

    }
}