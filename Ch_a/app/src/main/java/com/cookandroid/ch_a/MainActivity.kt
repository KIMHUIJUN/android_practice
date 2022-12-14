package com.cookandroid.ch_a

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.cookandroid.ch_a.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun saveData(height:Float,weight: Float){
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = pref.edit()

            editor.putFloat("KEY_HEIGHT",height)
                    .putFloat("KEY_WEIGHT",weight)
                    .apply()
        }
        fun loadData(){
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            val height = pref.getFloat("KEY_HEIGHT",0F)
            val weight = pref.getFloat("KEY_WEIGHT", 0F)

            if(height!=0F && weight !=0F){
                binding.heightEditText.setText(height.toString())
                binding.wiegtEditText.setText(weight.toString())
            }
        }
        loadData()
        binding.resultBtn.setOnClickListener {

            if(binding.heightEditText.text.isNotBlank() && binding.wiegtEditText.text.isNotBlank()){

                saveData(
                    binding.heightEditText.text.toString().toFloat(),
                    binding.wiegtEditText.text.toString().toFloat()
                )
                val intent = Intent (this,ResultActivity::class.java)
                intent.putExtra("height",binding.heightEditText.text.toString().toFloat())
                intent.putExtra("weight",binding.wiegtEditText.text.toString().toFloat())
                startActivity(intent)
            }
        }
    }
}