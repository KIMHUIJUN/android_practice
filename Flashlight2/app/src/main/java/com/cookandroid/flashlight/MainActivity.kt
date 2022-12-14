package com.cookandroid.flashlight

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.flashlight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val torch = Torch(this)

        binding.flashSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                startService(Intent(this, TorchService::class.java).apply {
                    action = "on"
                })
            }else{
                startService(Intent(this, TorchService::class.java).apply {
                    action = "off"
                })
            }
        }
    }
}