package com.cookandroid.homework

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.button.setOnClickListener{
            val intent = Intent(this,ResultActivity::class.java)
            startActivity(intent)
        }
        binding.button2.setOnClickListener{
            val intent = Intent(this,ResultActivity2::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:010-8526-8016")
                if(intent.resolveActivity(packageManager)!= null){
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text->{

                sendSMS("010-8526-8016","김희준 학생 모바일 프로그램 성적은 A+ 입니다.")

                return true
            }
            R.id.action_email -> {

                email("kymy8016@gmail.com","모바일 프로그램 성적","김희준 학생 모바일 프로그램 성적은 A+ 입니다.")

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}