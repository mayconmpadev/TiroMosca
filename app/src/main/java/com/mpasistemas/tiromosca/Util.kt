package com.mpasistemas.tiromosca

import kotlin.collections.ArrayList

open class Util {
    companion object{
        fun numeroAleatorio(): String {
            val lista = ArrayList<String>()
           lista.add ((0 until 10).random().toString())

            while (lista.size <= 4){
                val n1: String = (0 until 10).random().toString()
                if (!lista.contains(n1))
                    lista.add(n1)
            }
val numero = lista.get(0)+ lista.get(1)+ lista.get(2)+ lista.get(3)
return numero
        }
    }

}