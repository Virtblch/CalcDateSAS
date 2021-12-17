package com.example.calcdatesas

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

class SAStoTime : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sasto_time)


        val tv1: TextView = findViewById(R.id.tv1)
        val edt1: EditText = findViewById(R.id.edt1)
        val tvD: TextView = findViewById(R.id.tvD)
        val edtD: EditText = findViewById(R.id.edtD)
        val utc0Zone = ZoneId.of("UTC+0")

        edt1.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    val sasDate: Long = s.toString().toLong()
                    val i: Instant = Instant.ofEpochMilli(sasDate*1000 - 315619200000)
                    val aDateTime: ZonedDateTime = ZonedDateTime.ofInstant(i, utc0Zone)

                    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("ddMMMyyyy HH:mm:ss z")
                    val formattedString: String = aDateTime.format(formatter)
                    if (sasDate>=-11928470400 && sasDate<=566131766399) {
                        tv1.text = formattedString.replace("+","")
                    }
                    else{
                        var edtStr: String=(edt1.text).toString()
                        if (edtStr.isNotEmpty()) {
                            edtStr=edtStr.substring(0, edtStr.length -1)
                            edt1.setText(edtStr, TextView.BufferType.EDITABLE)
                            edt1.setSelection(edt1.text.length)
                        }

                    }
                } catch (e: Exception) {tv1.text=""}
            }
        })

        edtD.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    var sasDate: Long = s.toString().toLong()
                    sasDate *= 24 * 60 * 60
                    val i: Instant = Instant.ofEpochMilli(sasDate*1000 - 315619200000)
                    val aDateTime: ZonedDateTime = ZonedDateTime.ofInstant(i, utc0Zone)

                    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("ddMMMyyyy")
                    val formattedString: String = aDateTime.format(formatter)
                    if (sasDate>=-11928470400 && sasDate<=566131766399) {
                        tvD.text = formattedString.replace("+","")
                    }
                    else{
                        var edtStr: String=(edtD.text).toString()
                        if (edtStr.isNotEmpty()) {
                            edtStr = edtStr.substring(0, edtStr.length - 1)
                            edtD.setText(edtStr, TextView.BufferType.EDITABLE)
                            edtD.setSelection(edtD.text.length)
                        }
                    }
                } catch (e: Exception) {tvD.text=""}
            }
        })

    }
}