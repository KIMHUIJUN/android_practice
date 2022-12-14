package com.example.testfire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testfire.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding :ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.btnLogout.setOnClickListener {
            moveMainPage(auth?.currentUser)
        }
    }

    fun moveMainPage(user:FirebaseUser?){

        if (user!=null){
            Firebase.auth.signOut()

            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        startActivity(Intent(this,MainActivity::class.java))
    }
}