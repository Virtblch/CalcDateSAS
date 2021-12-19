package com.virtblch.calcdatesas

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button2: Button =findViewById(R.id.bt2)
        button2.setOnClickListener {
            intent = Intent(this, SAStoTime::class.java)
            startActivity(intent)
        }

        val button4: Button =findViewById(R.id.bt4)
        button4.setOnClickListener {
            intent = Intent(this, TimeToSAS::class.java)
            startActivity(intent)
        }

        val button3:Button=findViewById(R.id.bt3)
        button3.setOnClickListener {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://v8doc.sas.com/sashtml/lrcon/zenid-63.htm"))
            startActivity(intent)
        }
    }
}