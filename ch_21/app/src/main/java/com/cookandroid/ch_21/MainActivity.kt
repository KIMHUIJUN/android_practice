package com.cookandroid.ch_21

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.cookandroid.ch_21.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private fun saveData(height:Float, weight:Float){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putFloat("KEY_HEIGHT",height)
            .putFloat("KEY_WEIGHT",weight)
            .apply()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fun loadData(){
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            val height = pref.getFloat("KEY_HEIGHT", 0f)
            val weight = pref.getFloat("KEY_WEIGHT",0f)

            if(height != 0f && weight!= 0f ){
                binding.heightText.setText(height.toInt().toString())
                binding.weigthText.setText(weight.toInt().toString())
            }
        }
        loadData()
        binding.button.setOnClickListener {
            if(binding.heightText.text.isNotBlank()&& binding.weigthText.text.isNotBlank()){
                saveData(binding.heightText.text.toString().toFloat(),binding.weigthText.text.toString().toFloat())
                val intent = Intent(this,ResultActivity::class.java)

                intent.putExtra("height",binding.heightText.text.toString().toFloat())
                intent.putExtra("weight", binding.weigthText.text.toString().toFloat())
                startActivity(intent)
            }
        }
    }
}