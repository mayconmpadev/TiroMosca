package com.mpasistemas.tiromosca.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.mpasistemas.tiromosca.databinding.ItemUsuarioBinding
import com.mpasistemas.tiromosca.modelo.Usuario

class UsuariosAdapter(
    val context: Context,
    var clickCategoria: ClickCategoria
) :
    Adapter<UsuariosAdapter.MyViewHolder>() {

    private var documentList: MutableList<Usuario> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemUsuarioBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = documentList.size

    fun updateDocuments(newDocumentList: MutableList<Usuario>) {
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
        val usuario = documentList[position]

                holder.binding.textUsuario.text = usuario.nome

        holder.binding.root.setOnClickListener() {
            clickCategoria.clickCategoria(documentList[position])

        }
    }

    //interface
    interface ClickCategoria {
        fun clickCategoria(usuario : Usuario)
    }

    inner class MyViewHolder(val binding: ItemUsuarioBinding) : ViewHolder(binding.root) {

    }

}