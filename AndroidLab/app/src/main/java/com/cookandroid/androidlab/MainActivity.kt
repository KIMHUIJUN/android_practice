package com.cookandroid.androidlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btn1:Button =findViewById(R.id.btn1)
        btn1.setOnClickListener{

        }
    }
}