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
                    var sasDate: Long = s.toString().toLong()
                    val i: Instant = Instant.ofEpochMilli(sasDate*1000 - 315619200000)
                    val aDateTime: ZonedDateTime = ZonedDateTime.ofInstant(i, utc0Zone)

                    var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss z")
                    val formattedString: String = aDateTime.format(formatter)
                    if (sasDate>=-11928470400 && sasDate<=566131766399) {
                        tv1.text = formattedString
                    }
                    else{
                        tv1.text = "SAS DateTime out of range"
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
                    sasDate=sasDate*24*60*60
                    val i: Instant = Instant.ofEpochMilli(sasDate*1000 - 315619200000)
                    val aDateTime: ZonedDateTime = ZonedDateTime.ofInstant(i, utc0Zone)

                    var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
                    val formattedString: String = aDateTime.format(formatter)
                    if (sasDate>=-11928470400 && sasDate<=566131766399) {
                        tvD.text = formattedString
                    }
                    else{
                        tvD.text = "SAS Date out of range"
                    }
                } catch (e: Exception) {tvD.text=""}
            }
        })

    }
}