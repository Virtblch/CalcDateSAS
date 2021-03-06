package com.virtblch.calcdatesas

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat


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

        val tz = TimeZone.getDefault()

        /*Мин. и макс. значения, доступные в выборе календаря*/
        /*Судя по документации https://documentation.sas.com/doc/en/pgmsascdc/9.4_3.5/leforinforref/n0c9zxlm4e6m7tn1vrn76c98zw66.htm
        Даты SAS корректны с ноября 1582г. по 4000г.*/
        datePicker.minDate = tz.getOffset(0)-12217824000000L/*UnixTime= 1 November 1582 г., 0:00:00 с поправкой на часовой пояс*/
        datePicker.maxDate = 64060588799000L-tz.getOffset(0)/*UnixTime= 31 December 3999 г., 23:59:59 с поправкой на часовой пояс*/

        /*Все вычисления и вывод в нулевом часовом поясе*/
        val utc0Zone = ZoneId.of("UTC+0")

        /*Нулевая дата SAS*/
        val startSasDateTime: ZonedDateTime = ZonedDateTime.of(1960, 1, 1, 0, 0, 0, 0, utc0Zone)
        var ss: Long=0
        var mm: Long=0
        var hh: Long=0

        /*Текущая дата*/
        val calendar = Calendar.getInstance()
        val aDateTime: ZonedDateTime = ZonedDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0, 0, utc0Zone)

        /*Считаем разницу между нулевой датой SAS и текущей датой, преобразовывая милисек. в сек, это собственно и есть Sas DateTime.
        Ниже переменная sasDT пересчитывается при заполнении выпадающих списков с чч\мм\сс */
        sasDT = ChronoUnit.SECONDS.between(startSasDateTime, aDateTime)
        tv1.text=sasDT.toString()
        /*Из DateTime считаем SAS Date*/
        tvdt1.text=(sasDT!! / 24 / 60 / 60).toString()

        /*Инициализация и слушатель элемента выбора даты*/
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        { _, year, month, day ->
            /*Считаем новый SAS DateTime (и Date) после выбора новой даты. Учитывается выбор времени в выпадающих списках формы*/
            sasDT= ChronoUnit.SECONDS.between(startSasDateTime, ZonedDateTime.of(year, month+1, day, 0, 0, 0, 0, utc0Zone))
            tv1.text=(sasDT!! + TimeUnit.HOURS.toSeconds(hh)+ TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            tvdt1.text=(sasDT!! / 24 / 60 / 60).toString()
        }

        /*Слушатель выпадающего списка с выбором часа*/
        sphh.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
 //               if (sphh.selectedView != null) {
 //                   (sphh.selectedView as TextView).textSize = 20F
 //               }
                hh = selectedItemPosition.toString().toLong()
                /*Пересчет DATETIME SAS после выбора часа с учетом выбора в остальных выпадающих списках времени*/
                tv1.text=(sasDT!! + TimeUnit.HOURS.toSeconds(hh) + TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*Слушатель выпадающего списка с выбором минут*/
        spmm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
 //               if (spmm.selectedView != null) {
 //                   (spmm.selectedView as TextView).textSize = 20F
 //               }
                mm = selectedItemPosition.toString().toLong()
                /*Пересчет DATETIME SAS после выбора минут с учетом выбора в остальных выпадающих списках времени*/
                tv1.text=(sasDT!! + TimeUnit.HOURS.toSeconds(hh) + TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*Слушатель выпадающего списка с выбором секунд*/
        spss.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
//                if (spss.selectedView != null) {
//                    (spss.selectedView as TextView).textSize = 20F
//                }
                ss = selectedItemPosition.toString().toLong()
                /*Пересчет DATETIME SAS после выбора секунд с учетом выбора в остальных выпадающих списках времени*/
                tv1.text=(sasDT!! + TimeUnit.HOURS.toSeconds(hh) + TimeUnit.MINUTES.toSeconds(mm) + ss).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        /*Заполнение текущего времени UTC*/

        calendar.time = Date()
        calendar.add(Calendar.MILLISECOND, -(tz.getOffset(calendar.timeInMillis)))/*текущее время - часовой пояс*/
        sphh.setSelection(calendar.get(Calendar.HOUR_OF_DAY), true)
        spmm.setSelection(calendar.get(Calendar.MINUTE), true)
        spss.setSelection(calendar.get(Calendar.SECOND), true)

        //Копирование SAS DateTime в буфер по клику
        tv1.setOnClickListener(){
            if (tv1.text.isNotEmpty()) {
                val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
                clipboard?.setPrimaryClip(ClipData.newPlainText("", tv1.text))
                Toast.makeText(this, resources.getString(R.string.copy)+ " " + tv1.text,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //Копирование SAS Date в буфер по клику
        tvdt1.setOnClickListener(){
            if (tvdt1.text.isNotEmpty()) {
                val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
                clipboard?.setPrimaryClip(ClipData.newPlainText("", tvdt1.text))
                Toast.makeText(this, resources.getString(R.string.copy)+ " " + tvdt1.text,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    // сохранение состояния
    override fun onSaveInstanceState(outState: Bundle) {
        /*SAS DATE*/
        tvdt1 = findViewById(R.id.tvdt1)
        name = tvdt1.text.toString()
        nameVariableKey="tvdt1"
        outState.putString(nameVariableKey, name)
        /*переменная с SAS DATETIME*/
        name = sasDT.toString()
        nameVariableKey="sasDT"
        outState.putString(nameVariableKey, name)
        super.onSaveInstanceState(outState)
    }

    // получение ранее сохраненного состояния
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        /*SAS DATE*/
        nameVariableKey="tvdt1"
        name = savedInstanceState.getString(nameVariableKey)
        tvdt1.text = name
        /*переменная с SAS DATETIME*/
        nameVariableKey="sasDT"
        name = savedInstanceState.getString(nameVariableKey)
        sasDT= name?.toLong()

/*        if (sphh.selectedItemPosition==0) {
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
 */
        /*Если убрать то после добавления функционала заполнения текущего времени при смене ориентации едет верстка*/
        sphh.setSelection(sphh.selectedItemPosition, true)
        spmm.setSelection(spmm.selectedItemPosition, true)
        spss.setSelection(spss.selectedItemPosition, true)
    }
}