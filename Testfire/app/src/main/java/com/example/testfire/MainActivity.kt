package com.example.testfire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.testfire.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val RC_SIGN_IN =9001
    private var googleSignInClient:GoogleSignInClient? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1024002992295-f6b1plutevu7r1k56686gi8lg4ltcqd7.apps.googleusercontent.com")
            .requestEmail()
            .build()

        binding.signInButton.setOnClickListener {
            googleSignInClient = GoogleSignIn.getClient(this,gso)
            val signInIntent = googleSignInClient!!.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN)
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            var isGoToJoin = true

            if (email.isEmpty()){
                Toast.makeText(this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            if(password.isEmpty()){
                Toast.makeText(this,"비밀번호을 입력해주세요",Toast.LENGTH_SHORT).show()
                isGoToJoin = false

            }
            if (password.length < 6){
                Toast.makeText(this,"비밀번호를 6자리 이상으로 입력해주세요",Toast.LENGTH_SHORT).show()
                isGoToJoin = false

            }
            if (isGoToJoin){
                auth.createUserWithEmailAndPassword(email,password)
                    ?.addOnCompleteListener(this){task ->
                        if (task.isSuccessful){
                            Toast.makeText(this,"회원가입 성공",Toast.LENGTH_SHORT).show()
                            moveMainPage(auth?.currentUser)
                        }else{
                            Toast.makeText(this,"회원가입 실패",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            auth.signInWithEmailAndPassword(email,password)?.addOnCompleteListener {
                task ->
                if (task.isSuccessful){
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { sendTask->
                            if (sendTask.isSuccessful){
                                Toast.makeText(this,"메일 성공",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this,"메일 실패",Toast.LENGTH_SHORT).show()

                            }
                        }
                    Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show()
                    moveMainPage(auth?.currentUser)
                }else{
                    Toast.makeText(this,"로그인 실패", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    public override fun onStart() {
        super.onStart()
    }
    fun moveMainPage(user:FirebaseUser?){
        if (user!=null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e:ApiException){

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        firebaseAuthSignOut()
    }
    private fun firebaseAuthSignOut(){
        Firebase.auth.signOut()
        googleSignInClient!!.signOut()
    }
    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                val user = auth.currentUser
                user?.let {
                    val name = user.displayName
                    val email = user.email
                    val displayName = user.displayName
                    val photoUrl = user.photoUrl
                    val emailVerified = user.isEmailVerified
                    val uid = user.uid
                    Log.d("xxxxname",name.toString())

                    Log.d("xxxxemail",email.toString())

                    Log.d("xxxxdisplayName",email.toString())
                    moveMainPage(auth?.currentUser)
                }


            }else{
                Log.d("xxxx","signInWith Credential: failure",task.exception)
            }
        }
    }
}