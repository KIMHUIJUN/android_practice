package com.cookandroid.ch_12

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.ch_12.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private  var mBinding: ActivityMainBinding? =null

    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            val result = it.data?.getStringExtra("resultData")
            when(it.resultCode){
                RESULT_OK -> binding.txtview1.text = "$result"
                RESULT_CANCELED -> binding.txtview1.text = "$result"
            }
        }
        binding.button.setOnClickListener{
            val intent = Intent(this,AddActivity::class.java)
            intent.putExtra("data1","hello")
            intent.putExtra("data2",10)
            startActivityForResult(intent, 100)
        }
        binding.button4.setOnClickListener{
            val intent = Intent(this,AddActivity::class.java)
            intent.putExtra("data1","new Year")
            intent.putExtra("data2",2022)
            requestLauncher.launch(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            val result = data?.getStringExtra("resultData")
            binding.txtview1.text = "$result"
        }else if(requestCode == 100 && resultCode == Activity.RESULT_CANCELED){
            val result = data?.getStringExtra("resultData")
            binding.txtview1.text = "$result"
        }
    }
}