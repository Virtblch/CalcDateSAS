package com.example.calcdatesas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import java.util.*
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter


class DateToSAS : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_to_sas)

        val datePicker = findViewById<DatePicker>(R.id.dtp1)
        val tv1: TextView = findViewById(R.id.tv1)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))
        { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            tv1.text=msg

            val calendar = Calendar.getInstance()
            calendar.set(year, month-1, day)

            val calendarStart = Calendar.getInstance()
            calendarStart.timeInMillis = -315619200000


            val difference = calendar.timeInMillis - calendarStart.timeInMillis
            val days = difference / (24 * 60 * 60 * 1000)
            tv1.text=days.toString()
            //tv1.text=msg
            //tv1.text=calendar.time.toString()

        }


    }
}