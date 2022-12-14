package com.cookandroid.ch_20

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.cookandroid.ch_20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private fun saveData(height:Float ,weight:Float){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor =pref.edit()

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
            val height = pref.getFloat("KEY_HEIGHT",0f)
            val weight = pref.getFloat("KEY_WEIGHT",0f)

            if (weight != 0f && height != 0f){
                binding.heightText.setText(height.toString())
                binding.weightText.setText(weight.toString())
            }
        }
        loadData()
        binding.resultButton.setOnClickListener {
            if(binding.heightText.text.isNotBlank() && binding.weightText.text.isNotBlank()){

                saveData(binding.heightText.text.toString().toFloat(),binding.weightText.text.toString().toFloat())
                val intent = Intent(this,ResultActivity::class.java)
                intent.putExtra("height",binding.heightText.text.toString().toFloat())
                intent.putExtra("weight",binding.weightText.text.toString().toFloat())
                startActivity(intent)
        }}
    }

}