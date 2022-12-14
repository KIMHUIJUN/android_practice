package com.cookandroid.myapplication2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.myapplication2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reusultButton.setOnClickListener{
            val intent = Intent(this,ResultActivity::class.java)
            intent.putExtra("height",binding.height.text.toString().toFloat())
            intent.putExtra("weight",binding.weight.text.toString().toFloat())
            startActivity(intent)
        }
    }
}