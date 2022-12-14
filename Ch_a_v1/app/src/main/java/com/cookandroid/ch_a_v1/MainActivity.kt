package com.cookandroid.ch_a_v1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.cookandroid.ch_a_v1.databinding.ActivityMainBinding

package com.cookandroid.ch_a

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.cookandroid.ch_a_v1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun saveData(height:Float,weight: Float){
            val pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
            val editor = pref.edit()

            editor.putFloat("KEY_HEIGHT",height)
                .putFloat("KEY_WEIGHT",weight)
                .apply()
        }
        fun loadData(){
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            val height = pref.getInt("KEY_HEIGHT",0)
            val weight = pref.getInt("KEY_WEIGHT",0)

            if(height!=0 && weight !=0){
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