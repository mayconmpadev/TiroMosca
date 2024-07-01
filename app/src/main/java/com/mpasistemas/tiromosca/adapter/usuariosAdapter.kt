package com.mpasistemas.tiromosca.adapter

import android.content.Context
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

class usuariosAdapter  (val context: Context, var usuarioList: ArrayList<Usuario>,
                                     var clickCategoria: ClickCategoria, var ultimoItemExibidoRecyclerView: UltimoItemExibidoRecyclerView):
    RecyclerView.Adapter<usuariosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemUsuarioBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = usuarioList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = usuarioList[position]
        holder.binding.textUsuario.text = usuario.nome
        if (usuario.status == "online") {
            holder.binding.imageStatus.setImageResource(R.drawable.icone_disponivel)
        }
    }

    //interface
    interface ClickCategoria {
        fun clickCategoria(usuario: Usuario)
    }

    interface UltimoItemExibidoRecyclerView {
        fun ultimoItemExibidoRecyclerView(isExibido: Boolean)

    }
      inner class ViewHolder(val binding: ItemUsuarioBinding) :
            RecyclerView.ViewHolder(binding.root) {

        }

}