package com.virtblch.calcdatesas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SAStoTime : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sasto_time)

        /*Для вывода имени месяца англ. буквами*/
        Locale.setDefault(Locale.ENGLISH)

        val tv1: TextView = findViewById(R.id.tv1)
        val edt1: EditText = findViewById(R.id.edt1)
        val tvD: TextView = findViewById(R.id.tvD)
        val edtD: EditText = findViewById(R.id.edtD)

        /*Все вычисления и вывод в нулевом часовом поясе*/
        val utc0Zone = ZoneId.of("UTC+0")

        /*Слушатель ввода SAS DATETIME*/
        edt1.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    val sasDate: Long = s.toString().toLong()
                    /*Преобразование из SAS DATETIME в UNIXTIME, оно же javatime*/
                    val i: Instant = Instant.ofEpochMilli(sasDate*1000 - 315619200000)
                    val aDateTime: ZonedDateTime = ZonedDateTime.ofInstant(i, utc0Zone)

                    /*Форматирование даты\время */
                    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("ddMMMyyyy HH:mm:ss z")
                    val formattedString: String = aDateTime.format(formatter)

                    /*Судя по документации https://documentation.sas.com/doc/en/pgmsascdc/9.4_3.5/leforinforref/n0c9zxlm4e6m7tn1vrn76c98zw66.htm
                    Даты SAS корректны с ноября 1582г. по 4000г.*/
                    /*Если введенная дата SAS в диапазоне с ноября 1582г. по 4000г. */
                    if (sasDate>=-11902204800 && sasDate<=64376207999) {
                        tv1.text = formattedString.replace("+","")/*Если год >9999 то к году ставится "+", его удаляю*/
                    }
                    else{/*Иначе (если вне диапазона из документации) удаляем последний введенный символ*/
                        var edtStr: String=(edt1.text).toString()
                        if (edtStr.isNotEmpty()) {
                            edtStr=edtStr.substring(0, edtStr.length -1)
                            edt1.setText(edtStr, TextView.BufferType.EDITABLE)
                            edt1.setSelection(edt1.text.length)/*Фокус в конец строки*/
                        }

                    }
                } catch (e: Exception) {tv1.text=""}/*Если введены не цифры*/
            }
        })

        /*Слушатель ввода SAS DATE*/
        edtD.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    var sasDate: Long = s.toString().toLong()
                    /*Пересчет SAS DATE в DateTime*/
                    sasDate *= 24 * 60 * 60
                    /*Преобразование из SAS DATETIME в UNIXTIME, оно же javatime*/
                    val i: Instant = Instant.ofEpochMilli(sasDate*1000 - 315619200000)
                    val aDateTime: ZonedDateTime = ZonedDateTime.ofInstant(i, utc0Zone)

                    /*Форматирование даты */
                    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("ddMMMyyyy")
                    val formattedString: String = aDateTime.format(formatter)

                    /*Судя по документации https://documentation.sas.com/doc/en/pgmsascdc/9.4_3.5/leforinforref/n0c9zxlm4e6m7tn1vrn76c98zw66.htm
                    Даты SAS корректны с ноября 1582г. по 4000г.*/
                    /*Если введенная дата SAS в диапазоне с ноября 1582г. по 4000г. */
                    if (sasDate>=-11902204800 && sasDate<=64376207999) {
                        tvD.text = formattedString.replace("+","")/*Если год >9999 то к году ставится "+", его удаляю*/
                    }
                    else{/*Иначе (если вне диапазона из документации) удаляем последний введенный символ*/
                        var edtStr: String=(edtD.text).toString()
                        if (edtStr.isNotEmpty()) {
                            edtStr = edtStr.substring(0, edtStr.length - 1)
                            edtD.setText(edtStr, TextView.BufferType.EDITABLE)
                            edtD.setSelection(edtD.text.length)/*Фокус в конец строки*/
                        }
                    }
                } catch (e: Exception) {tvD.text=""}/*Если введены не цифры*/
            }
        })

    }
}