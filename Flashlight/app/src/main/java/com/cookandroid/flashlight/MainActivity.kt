package com.cookandroid.flashlight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this,TorchService::class.java)

        val torch = Torch(this)
        flashswitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                intent.action = "on"
                startService(intent)
            }
            else{
                intent.action = "off"
                startService(intent)
            }
        }

    }
}
