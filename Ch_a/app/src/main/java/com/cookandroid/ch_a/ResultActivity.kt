package com.cookandroid.ch_a

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.ch_a.databinding.ActivityResultBinding
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val height = intent.getFloatExtra("height",0f)
        val weight = intent.getFloatExtra("weight",0f)

        val bmi = weight/(height /100.0f).pow(2.0f)
        Log.d("비만도","$height, $weight $bmi")

    when {
        bmi >= 35 -> binding.resultTextView.text = "고도비만"
        bmi >= 30 -> binding.resultTextView.text = "2단계비만"
        bmi >= 25 -> binding.resultTextView.text = "1단계비만"
        bmi >= 23 -> binding.resultTextView.text = "과체중"
        bmi >= 18.5 -> binding.resultTextView.text = "정상"
        else ->binding.resultTextView.text = "저체중"
    }

    when {
        bmi >= 25 -> binding.imageView.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
        bmi >= 23 -> binding.imageView.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_24)
        bmi >= 18.5 -> binding.imageView.setImageResource(R.drawable.ic_baseline_sentiment_very_satisfied_24)
        else ->binding.imageView.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
    }
    Toast.makeText(this,"$bmi",Toast.LENGTH_SHORT).show()
}
}