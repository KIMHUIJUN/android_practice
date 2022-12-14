package com.cookandroid.ch_20

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.ch_20.databinding.ActivityResultBinding
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val height = intent.getFloatExtra("height",0f)
        val weight = intent.getFloatExtra("weight",0f)

        val bmi = weight / (height/ 100.0f).pow(2.0f)
        Log.d("비만도","$weight $height $bmi")

        when{
            bmi >= 35 -> binding.textView2.text = "고도비만"
            bmi >= 30 -> binding.textView2.text = "2단계 비만"
            bmi >= 25 -> binding.textView2.text = "1단계 비만"
            bmi >= 23 -> binding.textView2.text = "과체중"
            bmi >= 18.5 -> binding.textView2.text = "정상"
            else -> binding.textView2.text = "저체중"
        }
        when{
            bmi >= 23 -> binding.imageView2.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)

            bmi >= 18.5 -> binding.imageView2.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)

            else-> binding.imageView2.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_24)
        }

        Toast.makeText(this,"$bmi",Toast.LENGTH_SHORT).show()
    }
}