package com.mpasistemas.tiromosca.modelo

import android.os.Parcel
import android.os.Parcelable


data class Usuario(

    var id: String = "",
    var token: String = "",
    var nome: String = "",
    var data: String = "",
    var status: String = "",
    var vitorias: Int = 0,
    var derrotas: Int = 0,
    var pontuacao: Int = 0,
    var partidas: Int = 0,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?:"",
        parcel.readString() ?:"",
        parcel.readString() ?:"",
        parcel.readString() ?:"",
        parcel.readString() ?:"",
        parcel.readInt() ,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun describeContents(): Int {
       return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
parcel.writeString(id)
parcel.writeString(token)
parcel.writeString(nome)
parcel.writeString(data)
parcel.writeString(status)
parcel.writeInt(vitorias)
parcel.writeInt(derrotas)
parcel.writeInt(vitorias)
parcel.writeInt(partidas)
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
