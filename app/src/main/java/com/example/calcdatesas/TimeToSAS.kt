package com.example.calcdatesas

import android.os.Bundle
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit
import android.widget.AdapterView
import android.view.View


class TimeToSAS : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_to_sas)

        val datePicker = findViewById<DatePicker>(R.id.dtp1)
        val tv1: TextView = findViewById(R.id.tv1)
        val tvdt1: TextView = findViewById(R.id.tvdt1)
        val sphh: Spinner = findViewById(R.id.sphh)
        val spmm: Spinner = findViewById(R.id.spmm)
        val spss: Spinner = findViewById(R.id.spss)
        datePicker.setMinDate(864000000L-12244089600000L)
        datePicker.setMaxDate(565816147198999L-86400000L)
        val utc0Zone = ZoneId.of("UTC+0")
        val startSasDateTime: ZonedDateTime = ZonedDateTime.of(1960, 1, 1, 0, 0, 0, 0, utc0Zone)
        var ss: Long=0
        var mm: Long=0
        var hh: Long=0

        val calendar = Calendar.getInstance()
        val aDateTime: ZonedDateTime = ZonedDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0, 0, utc0Zone)
        var sasDT: Long = ChronoUnit.SECONDS.between(startSasDateTime, aDateTime)
        tv1.text=sasDT.toString()
        tvdt1.text=(sasDT/ 24 / 60 / 60).toString()

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        { _, year, month, day ->
            val month = month + 1

            val aDateTime: ZonedDateTime = ZonedDateTime.of(year, month, day, 0, 0, 0, 0, utc0Zone)
            sasDT= ChronoUnit.SECONDS.between(startSasDateTime, aDateTime)
            tv1.text=(sasDT + TimeUnit.HOURS.toSeconds(hh)+ TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            tvdt1.text=(sasDT/ 24 / 60 / 60).toString()
        }

        sphh.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                hh = selectedItemPosition.toString().toLong()
                tv1.text=(sasDT + TimeUnit.HOURS.toSeconds(hh) + TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        spmm.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                mm = selectedItemPosition.toString().toLong()
                tv1.text=(sasDT + TimeUnit.HOURS.toSeconds(hh) + TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        spss.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                ss = selectedItemPosition.toString().toLong()
                tv1.text=(sasDT + TimeUnit.HOURS.toSeconds(hh) + TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }
}