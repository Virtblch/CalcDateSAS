package com.example.calcdatesas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button =findViewById(R.id.bt1)
        button.setOnClickListener {
            intent = Intent(this, SAStoDate::class.java)
            startActivity(intent)
        }

        val button2: Button =findViewById(R.id.bt2)
        button2.setOnClickListener {
            intent = Intent(this, SAStoTime::class.java)
            startActivity(intent)
        }

 /*       val button3: Button =findViewById(R.id.bt3)
        button3.setOnClickListener {
            intent = Intent(this, DateToSAS::class.java)
            startActivity(intent)
        }*/

        val button4: Button =findViewById(R.id.bt4)
        button4.setOnClickListener {
            intent = Intent(this, TimeToSAS::class.java)
            startActivity(intent)
        }
    }
}