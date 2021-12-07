package com.example.calcdatesas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeToSAS : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_to_sas)

        val datePicker = findViewById<DatePicker>(R.id.dtp1)
        val tv1: TextView = findViewById(R.id.tv1)
        val ethh: TextView = findViewById(R.id.ethh)
        val etmm: TextView = findViewById(R.id.etmm)
        val etss: TextView = findViewById(R.id.etss)

        val today = Calendar.getInstance()
        today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
        val hh1 = Calendar.getInstance()
        hh1.timeInMillis=0
        val mm1 = Calendar.getInstance()
        mm1.timeInMillis=0
        val ss1 = Calendar.getInstance()
        ss1.timeInMillis=0
        val time1 = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))
        { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            tv1.text=msg


            calendar.set(year, month-1, day, 0, 0, 0)
            today.set(year, month-1, day, 0, 0, 0)

            val calendarStart = Calendar.getInstance()
            calendarStart.timeInMillis = -315619200000


            val difference = calendar.timeInMillis - calendarStart.timeInMillis
            //val days = difference / (24 * 60 * 60 * 1000)
            tv1.text=((difference + ss1.timeInMillis + mm1.timeInMillis + hh1.timeInMillis)/1000).toString()
            //tv1.text=msg
            //tv1.text=calendar.time.toString()




        }

        ethh.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    var hh: Long = s.toString().toLong()
                    var pMils: Long

                    pMils= TimeUnit.HOURS.toMillis(hh)
                    hh1.timeInMillis=pMils
                    time1.timeInMillis=today.timeInMillis + pMils + 315619200000 + mm1.timeInMillis + ss1.timeInMillis
                    tv1.text=(time1.timeInMillis/1000).toString()
                    //tv1.text=today.timeInMillis.toString()

                } catch (e: Exception) {tv1.text=((today.timeInMillis + 315619200000 + mm1.timeInMillis + ss1.timeInMillis)/1000).toString(); hh1.timeInMillis=0}
            }
        })

        etmm.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    var mm: Long = s.toString().toLong()
                    var pMils: Long

                    pMils= TimeUnit.MINUTES.toMillis(mm)
                    mm1.timeInMillis=pMils
                    time1.timeInMillis=today.timeInMillis + pMils + 315619200000 + hh1.timeInMillis + ss1.timeInMillis
                    tv1.text=(time1.timeInMillis/1000).toString()
                    //tv1.text=today.timeInMillis.toString()

                } catch (e: Exception) {tv1.text=((today.timeInMillis + 315619200000 + hh1.timeInMillis + ss1.timeInMillis)/1000).toString(); mm1.timeInMillis=0}
            }
        })

        etss.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    var ss: Long = s.toString().toLong()
                    var pMils: Long

                    pMils= TimeUnit.SECONDS.toMillis(ss)
                    ss1.timeInMillis=pMils
                    time1.timeInMillis=today.timeInMillis + pMils + 315619200000 + mm1.timeInMillis + hh1.timeInMillis
                    tv1.text=(time1.timeInMillis/1000).toString()
                    //tv1.text=today.timeInMillis.toString()

                } catch (e: Exception) {tv1.text=((today.timeInMillis + 315619200000 + mm1.timeInMillis + hh1.timeInMillis)/1000).toString(); ss1.timeInMillis=0}
            }
        })

    }
}