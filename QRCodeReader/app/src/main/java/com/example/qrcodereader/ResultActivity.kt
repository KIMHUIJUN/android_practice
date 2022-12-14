package com.example.qrcodereader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.qrcodereader.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityResultBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val result = intent.getStringExtra("msg")?:"데이터가 존재 하지않습니다"
        setUI(result)
    }
    private fun setUI(result:String){
        binding.tvContent.text = result
        binding.btnGoback.setOnClickListener{
            finish()
        }
    }
}