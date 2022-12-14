package com.cookandroid.myapplication2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.myapplication2.databinding.ActivityResultBinding
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding  = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val height = intent.getFloatExtra("height", 0f)
        val weight = intent.getFloatExtra("weight", 0f)

        val bmi = weight / (height/100.0f).pow(2.0f)
        Log.d("비만도","$height $weight  $bmi")
    }
}