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
        val edt1: EditText = findViewById(R.id.edt1)


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


                    if (sasDate<0){
                        pMils = -(TimeUnit.SECONDS.toMillis(Math.abs(sasDate)) + 315619200000)
                    }
                    else{
                        pMils= TimeUnit.SECONDS.toMillis(sasDate) - 315619200000
                    }
                    //var formatter = SimpleDateFormat1("yyyy-MM-dd HH:mm:ss") // modify format
                    tv1.text = formatter.format(Date(pMils))
                } catch (e: Exception) {tv1.text=""}
            }
        })

    }
}