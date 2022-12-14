package com.cookandroid.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import com.cookandroid.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }
        binding.webView.loadUrl("https://www.google.com")

        binding.urlEditText.setOnEditorActionListener{_, actionId , _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                binding.webView.loadUrl(binding.urlEditText.text.toString())
                true
            }
            else{
                false
            }
        }
        registerForContextMenu(binding.webView)
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_google,R.id.action_home ->{
                binding.webView.loadUrl("https://www.google.com")
                return true
            }
            R.id.action_naver ->{
                binding.webView.loadUrl("https://www.naver.com")
                return true
            }
            R.id.action_daum ->{
                binding.webView.loadUrl("https://www.daum.com")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("010-8526-8016")
                if(intent.resolveActivity(packageManager)!=null){
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send ->{
                binding.webView.url?.let { url ->
                    sendSMS("010-8526-8016",url)
                }
                return true
            }

            R.id.action_email ->{
                binding.webView.url?.let { url ->
                    email("010-8526-8016","좋은 사이트",url)
                }
                return true
            }
        }
        return true

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_browser ->{
                return true
            }
            R.id.action_share ->{
                return true
            }

        }
        return super.onContextItemSelected(item)
    }
}
