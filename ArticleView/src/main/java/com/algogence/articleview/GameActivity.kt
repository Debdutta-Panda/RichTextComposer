package com.algogence.articleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {
    private var game_view: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        game_view = findViewById(R.id.game_view)
        if(savedInstanceState==null){
            game_view?.settings?.javaScriptEnabled = true
            game_view?.webViewClient = WebViewClient()
            game_view?.webChromeClient = WebChromeClient()
            game_view?.loadUrl(intent?.getStringExtra("url")?:"")
            lifecycleScope.launch {
                val r = RestClient().get(intent?.getStringExtra("url")?:"")
                val s = r.data
                Log.d("fdfdfdf",s.toString())
            }

        }
    }
}