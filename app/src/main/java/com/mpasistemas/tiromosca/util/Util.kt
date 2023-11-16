package com.mpasistemas.tiromosca.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import java.math.BigDecimal
import java.text.Normalizer

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


        open fun textoNegrito(texto: String, textView: TextView?, editText: EditText?) {
            val text = SpannableString(texto.replace("+", "").replace("/", "").replace("*", ""))
            var a = 0
            var b = texto.length
            var c = 0
            val d = 0
            val e = texto.length
            val f = 0
            for (i in 0 until texto.length) {
                if ("+" == texto.substring(i, i + 1) || "/" == texto.substring(
                        i,
                        i + 1
                    ) || "*" == texto.substring(i, i + 1)
                ) {
                    if (b == texto.length) {
                        b = a
                    } else {
                        c = a - 1
                        if ("+" == texto.substring(i, i + 1)) {
                            text.setSpan(UnderlineSpan(), b, c, 0)
                            text.setSpan(StyleSpan(Typeface.BOLD), b, c, 0)
                        } else if ("/" == texto.substring(i, i + 1)) {
                            text.setSpan(UnderlineSpan(), b, c, 0)
                        } else if ("*" == texto.substring(i, i + 1)) {
                            text.setSpan(ForegroundColorSpan(Color.BLACK), b, c, 0)
                            text.setSpan(StyleSpan(Typeface.BOLD), b, c, 0)
                        }
                        a--
                        b = texto.length
                        c = 0
                        a--
                    }
                }
                a++
            }

            // text.setSpan(new ForegroundColorSpan(Color.RED), negrito.get(i).comeco, negrito.get(i).fim - 1, 0);
            // text.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), negrito.get(i).comeco - 1, negrito.get(i).comeco, 0);
            // text.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), negrito.get(i).fim - 1, negrito.get(i).fim, 0);
            // text.setSpan(new ForegroundColorSpan(Color.RED), 5, 9, 0);
            if (textView == null) {
                editText?.setText(text, TextView.BufferType.SPANNABLE)
            } else {
                textView.setText(text, TextView.BufferType.SPANNABLE)
            }
        }
    }

    open fun verificarInternet(context: Context): Boolean {
        val conexao = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val informacao = conexao.activeNetworkInfo
        return if (informacao != null && informacao.isConnected) {
            true
        } else {
            false
        }
    }



    open fun vibrar(context: Context, milisegundos: Int) {
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(
                VibrationEffect.createOneShot(
                    milisegundos.toLong(),
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            //deprecated in API 26
            v.vibrate(milisegundos.toLong())
        }
    }

    open fun activity(context: Context?): Activity? {
        var context = context
        while (context !is Activity) {
            if (context !is ContextWrapper) {
                context = null
            }
            val contextWrapper = context as ContextWrapper? ?: return null
            context = contextWrapper.baseContext
            if (context == null) {
                return null
            }
        }
        return context
    }

    open fun convertMoneEmBigDecimal(monetaria: String): BigDecimal? {
        var bigDecimal: BigDecimal?
        var vazia = ""
        try {
            vazia = monetaria.replace("R$", "")
            vazia = vazia.replace("\u00A0", "")
            vazia = vazia.replace(",", "")
            vazia = vazia.replace(".", "")
            vazia = vazia.trim { it <= ' ' }
            Log.i("bigdecimal", vazia)
            bigDecimal = BigDecimal(vazia)
        } catch (e: Exception) {
            bigDecimal = BigDecimal("0.00")
            Log.i("bigdecimal", vazia)
        }
        return bigDecimal
    }

    open fun removerAcentos(texto: String?): String? {
        val textoSemAcento = Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replace("[^\\p{ASCII}]".toRegex(), "")
        return textoSemAcento.uppercase()
    }
}