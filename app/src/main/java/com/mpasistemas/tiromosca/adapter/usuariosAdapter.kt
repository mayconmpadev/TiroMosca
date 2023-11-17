package com.mpasistemas.tiromosca.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.databinding.ItemUsuarioBinding
import com.mpasistemas.tiromosca.modelo.Usuario

class usuariosAdapter (private val usuarioList : List<Usuario>) : Adapter<usuariosAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater =  LayoutInflater.from(parent.context)
     val itemView = ItemUsuarioBinding.inflate(layoutInflater,parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = usuarioList.size

    override fun onBindViewHolder(holder: usuariosAdapter.MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
   inner class MyViewHolder(val binding: ItemUsuarioBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}