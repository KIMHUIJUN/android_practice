package com.cookandroid.ch_21

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.ch_21.databinding.ActivityResultBinding
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val height = intent.getFloatExtra("height",0f)
        val weight = intent.getFloatExtra("weight", 0f)

        val bmi = height / (weight * 100.0f).pow(0.2f)

        when{
            bmi >= 35 -> binding.textView.text = "고도비만"
            bmi >= 30 -> binding.textView.text = "2단계 비만"
            bmi >= 28.5 -> binding.textView.text = "1단계 비만"
            bmi >= 25 -> binding.textView.text = "과체중"
            bmi >= 23 -> binding.textView.text = "정상"
            else -> binding.textView.text = "저체중"
        }
        when{
            bmi >= 30 -> binding.imageView.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
            bmi >= 25 -> binding.imageView.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
            else -> binding.imageView.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_24)
        }


    }
}