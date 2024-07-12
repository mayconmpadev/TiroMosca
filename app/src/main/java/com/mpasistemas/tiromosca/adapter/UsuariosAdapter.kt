package com.mpasistemas.tiromosca.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.databinding.ItemUsuarioBinding
import com.mpasistemas.tiromosca.modelo.Usuario

class UsuariosAdapter  (val context: Context, var usuarioList: ArrayList<Usuario>,
                        var clickCategoria: ClickCategoria):
    Adapter<UsuariosAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemUsuarioBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = usuarioList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val usuario = usuarioList[position]
        holder.binding.textUsuario.text = usuario.nome


        holder.binding.root.setOnClickListener(){
            clickCategoria.clickCategoria(usuarioList[position])

        }

    }

    //interface
    interface ClickCategoria {
        fun clickCategoria(usuario: Usuario)
    }


      inner class MyViewHolder(val binding: ItemUsuarioBinding) :
            ViewHolder(binding.root) {

        }

}