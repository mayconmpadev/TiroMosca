package com.mpasistemas.tiromosca.util

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

open class Timestamp {
    /**
     * Obtêm o Timestamp em segundos desde 1/1/1970
     *
     * @return timestamp em segundos
     */

    companion object{
        open  fun getUnixTimestamp(): Long {
            // create a calendar
            val cal = Calendar.getInstance()
            return cal.timeInMillis / 1000
        }

        /**
         * Obtêm a data e hora no formato especificado a partir do timestamp em ms
         *
         * @param timestamp DateTimeHandler em milisegundos
         * @return Retorna uma string contendo a data e hora no formato desejado
         */

        open fun getFormatedDateTime(timestamp: Long, format: String?): String? {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = timestamp * 1000
            return DateFormat.format(format, cal).toString()
        }

        open fun convert(data: String): String? {
            val calendar = Calendar.getInstance()
            calendar[Calendar.YEAR] = data.substring(6, 10).toInt()
            calendar[Calendar.MONTH] = data.substring(3, 5).toInt() - 1
            calendar[Calendar.DAY_OF_MONTH] = data.substring(0, 2).toInt()
            val date_ship_millis = calendar.timeInMillis / 1000
            return date_ship_millis.toString()
        }
    }

}