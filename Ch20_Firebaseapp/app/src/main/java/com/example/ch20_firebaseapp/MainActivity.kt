package com.example.ch20_firebaseapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ch20_firebaseapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        auth = Firebase.auth

        binding.btnSignIn.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            auth.createUserWithEmailAndPassword(email,password)
                ?.addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "회원가입 성공",Toast.LENGTH_SHORT).show()

                    }
                    else{
                        Toast.makeText(this, "회원가입 실패",Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}