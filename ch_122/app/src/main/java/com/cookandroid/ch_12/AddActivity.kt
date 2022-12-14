package com.cookandroid.ch_12

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.ch_12.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data1 = intent.getStringExtra("data1")
        val data2 = intent.getIntExtra("data2",0)

        binding.textView1.text = "$data1"
        binding.textView2.text = "$data2"
        binding.button2.setOnClickListener{
            val intent = intent
            intent.putExtra("resultData","return value OK")
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
        binding.button4.setOnClickListener{
            val intent = intent
            intent.putExtra("resultData","return value cancle")
            setResult(Activity.RESULT_CANCELED,intent)
            finish()
        }

    }
}