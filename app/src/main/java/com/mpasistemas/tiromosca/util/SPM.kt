package com.mpasistemas.tiromosca.util

import android.content.Context

class SPM(mContext: Context) {
    private var context: Context = mContext

    fun getPreferencia(sharedPreferencesFile: String, chave: String, valorNulo: String): String {

        /*Inicia uma instância do arquivo*/
        val sharedPreferences =
            context.getSharedPreferences(sharedPreferencesFile, Context.MODE_PRIVATE)
        val preferencia = sharedPreferences.getString(chave, valorNulo)
        return preferencia!!
    }

    fun setPreferencia(SharedPreferencesFile: String?, chave: String?, valor: String?) {
        /*Inicia uma instância do arquivo*/
        val sharedPreferences =
            context.getSharedPreferences(SharedPreferencesFile, Context.MODE_PRIVATE)
        /*Inicia uma instância do editor*/
        val editor = sharedPreferences.edit()
        /*Insere a chave e valor no arquivo*/
        editor.putString(chave, valor)
        /*Aplica a modificação*/
        editor.apply()
    }

}