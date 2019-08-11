package br.com.brb.savemovies.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object ConvertData {


    fun generateDataRequest(titleMovie: String): String {

        val words = titleMovie.split(" ")
        var i = 0

        val sb = StringBuilder("")
        while (i < words.size - 1) {
            sb.append(words[i])
            sb.append("+")
            i++
        }
        sb.append(words[words.size - 1])


        return sb.toString()
    }


    @Throws(ParseException::class)
    fun formatStringToDate(date: String): Date {
        val fmt = SimpleDateFormat("dd-MMM-yyyy", Locale("pt", "BR"))

        return fmt.parse(date)
    }


    fun firstWordUppercase(word: String): String {
        val sb = StringBuilder(word)
        sb.setCharAt(0, Character.toUpperCase(sb[0]))

        return sb.toString()

    }


    fun formatRunTime(runTime: String): String {
        val time = Integer.parseInt(runTime.split(" ")[0])

        return if (time >= 60) {
            val hour = time / 60
            val minutes = time % 60

            hour.toString() + "h" + minutes.toString() + "min"
        } else {
            runTime
        }
    }
}