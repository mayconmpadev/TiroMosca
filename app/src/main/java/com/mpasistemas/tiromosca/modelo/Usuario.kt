package com.mpasistemas.tiromosca.modelo

import android.os.Parcel
import android.os.Parcelable


data class Usuario(

    var id: String = "",
    var nome: String = "",
    var data: String = "",
    var status: String = "",
    var vitorias: Int = 0,
    var derrotas: Int = 0,
    var pontuacao: Double = 0.0,
    var partidas: Int = 0,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?:"",
        parcel.readString() ?:"",
        parcel.readString() ?:"",
        parcel.readString() ?:"",
        parcel.readInt() ,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }
}
