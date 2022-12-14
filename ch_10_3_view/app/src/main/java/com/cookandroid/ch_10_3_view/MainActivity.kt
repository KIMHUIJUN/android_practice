package com.cookandroid.ch_10_3_view

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.ch_10_3_view.databinding.ActivityMainBinding
import com.cookandroid.ch_10_3_view.databinding.DialogInputBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button1.setOnClickListener {
            val dialogBinding = DialogInputBinding.inflate(layoutInflater)
            AlertDialog.Builder(this).run {
                setTitle("Input")
                setView(dialogBinding.root)
                setCancelable(false)
                setPositiveButton("닫기", null)
                show()
            }.setCanceledOnTouchOutside(false)
        }
    }
}