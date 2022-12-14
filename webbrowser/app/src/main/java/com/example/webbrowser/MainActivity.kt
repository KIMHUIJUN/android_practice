package com.example.webbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var webview : WebView = findViewById<WebView>(R.id.webview)
        var editurl:EditText = findViewById<EditText>(R.id.urktext)

        webview.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }
        webview.loadUrl("http://www.google.com")

        editurl.setOnEditorActionListener { view, actionId, event ->
            if ((actionId == EditorInfo.IME_ACTION_SEARCH) ||(event.action == KeyEvent.ACTION_DOWN) || (actionId == KeyEvent.KEYCODE_ENTER)){
                var webburi = editurl.text.toString()
                if (webburi.startsWith("http://")){
                    webview.loadUrl(webburi)
                }else{
                    webview.loadUrl("http://" + webburi)
                }
                true
            }
            else{
                false
            }
        }
    }
    override fun onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack()
        }
        else{
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.google -> {
                webview.loadUrl("http://www.google.com")
            }
            R.id.naver -> {
                webview.loadUrl("http://www.naver.com")
            }
            R.id.email -> {
                webview.loadUrl("https://www.google.com/intl/ko/gmail/about/#")
            }
            R.id.home ->{
                webview.loadUrl("http://www.daum.net")
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context,menu)
    }
}
