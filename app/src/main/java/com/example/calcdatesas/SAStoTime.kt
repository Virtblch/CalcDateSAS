package com.example.calcdatesas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class SAStoTime : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sasto_time)


        val tv1: TextView = findViewById(R.id.tv1)
        val lbl: TextView = findViewById(R.id.lbl)
        val edt1: EditText = findViewById(R.id.edt1)

        val tz = TimeZone.getDefault().id
        lbl.text="Date and Time $tz:"

        edt1.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    var sasDate: Long = s.toString().toLong()
                    var pMils: Long
                    var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss") // modify format


                    if (sasDate<0 && sasDate>=-11897020800){
                        pMils = -(TimeUnit.SECONDS.toMillis(Math.abs(sasDate)) + 315619200000)
                        tv1.text = formatter.format(Date(pMils))
                    }
                    else if(sasDate>=0 && sasDate<=566131680000){
                        pMils= TimeUnit.SECONDS.toMillis(sasDate) - 315619200000
                        tv1.text = formatter.format(Date(pMils))
                    }
                    else{
                        tv1.text = "out of range"
                    }
                    //var formatter = SimpleDateFormat1("yyyy-MM-dd HH:mm:ss") // modify format

                } catch (e: Exception) {tv1.text=""}
            }
        })

    }
}