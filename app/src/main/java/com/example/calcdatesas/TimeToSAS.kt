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
    private var name: String? = "undefined"
    private var nameVariableKey = "NAME_VARIABLE"
    private var sasDT: Long? = 0
    private lateinit var tvdt1: TextView
    private lateinit var sphh: Spinner
    private lateinit var spmm: Spinner
    private lateinit var spss: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_to_sas)

        val datePicker = findViewById<DatePicker>(R.id.dtp1)
        val tv1: TextView = findViewById(R.id.tv1)
        tvdt1 = findViewById(R.id.tvdt1)
        sphh= findViewById(R.id.sphh)
        spmm= findViewById(R.id.spmm)
        spss= findViewById(R.id.spss)
        datePicker.minDate = 864000000L-12244089600000L
        datePicker.maxDate = 565816147198999L-86400000L
        val utc0Zone = ZoneId.of("UTC+0")
        val startSasDateTime: ZonedDateTime = ZonedDateTime.of(1960, 1, 1, 0, 0, 0, 0, utc0Zone)
        var ss: Long=0
        var mm: Long=0
        var hh: Long=0

        val calendar = Calendar.getInstance()
        val aDateTime: ZonedDateTime = ZonedDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0, 0, utc0Zone)
        sasDT = ChronoUnit.SECONDS.between(startSasDateTime, aDateTime)
        tv1.text=sasDT.toString()
        tvdt1.text=(sasDT!! / 24 / 60 / 60).toString()

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        { _, year, month, day ->
            /*val month = month + 1*/

            /*val aDateTime: ZonedDateTime = ZonedDateTime.of(year, month+1, day, 0, 0, 0, 0, utc0Zone)*/
            sasDT= ChronoUnit.SECONDS.between(startSasDateTime, ZonedDateTime.of(year, month+1, day, 0, 0, 0, 0, utc0Zone))
            tv1.text=(sasDT!! + TimeUnit.HOURS.toSeconds(hh)+ TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            tvdt1.text=(sasDT!! / 24 / 60 / 60).toString()
        }

        sphh.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                if (sphh.selectedView != null) {
                    (sphh.selectedView as TextView).textSize = 20F
                }
                hh = selectedItemPosition.toString().toLong()
                tv1.text=(sasDT!! + TimeUnit.HOURS.toSeconds(hh) + TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spmm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                if (spmm.selectedView != null) {
                    (spmm.selectedView as TextView).textSize = 20F
                }
                mm = selectedItemPosition.toString().toLong()
                tv1.text=(sasDT!! + TimeUnit.HOURS.toSeconds(hh) + TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spss.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                if (spss.selectedView != null) {
                    (spss.selectedView as TextView).textSize = 20F
                }
                ss = selectedItemPosition.toString().toLong()
                tv1.text=(sasDT!! + TimeUnit.HOURS.toSeconds(hh) + TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    // сохранение состояния
    override fun onSaveInstanceState(outState: Bundle) {
        tvdt1 = findViewById(R.id.tvdt1)
        name = tvdt1.text.toString()
        nameVariableKey="tvdt1"
        outState.putString(nameVariableKey, name)
        name = sasDT.toString()
        nameVariableKey="sasDT"
        outState.putString(nameVariableKey, name)
        super.onSaveInstanceState(outState)
    }

    // получение ранее сохраненного состояния
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        nameVariableKey="tvdt1"
        name = savedInstanceState.getString(nameVariableKey)
        tvdt1.text = name
        nameVariableKey="sasDT"
        name = savedInstanceState.getString(nameVariableKey)
        sasDT= name?.toLong()

        if (sphh.selectedItemPosition==0) {
            sphh.setSelection(0, true)
            val vh: View = sphh.selectedView; (vh as TextView).textSize = 20F
        }
        if (spmm.selectedItemPosition==0) {
            spmm.setSelection(0, true)
            val vm: View = spmm.selectedView; (vm as TextView).textSize = 20F
        }
        if (spss.selectedItemPosition==0) {
            spss.setSelection(0, true)
            val vs: View = spss.selectedView; (vs as TextView).textSize = 20F
        }
    }
}