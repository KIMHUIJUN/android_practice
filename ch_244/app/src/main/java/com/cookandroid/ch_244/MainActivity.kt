package com.cookandroid.ch_244

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.ch_244.databinding.ActivityMainBinding

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

        binding.urlEditText.setOnEditorActionListener{ _, actionId,_ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                binding.webView.loadUrl(binding.urlEditText.text.toString())
                true
            }
            else{
                false
            }
        }

    }

    override fun onBackPressed() {

        if (binding.webView.canGoBack()){
            binding.webView.goBack()
        }

        else{

        super.onBackPressed()
        }
    }
}