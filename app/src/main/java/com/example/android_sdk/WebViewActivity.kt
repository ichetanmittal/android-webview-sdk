package com.example.android_sdk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    companion object {
        private const val EXTRA_URL = "extra_url"
        private const val DEFAULT_URL = "https://xaults.com"

        @JvmStatic
        fun launch(context: Context, url: String = DEFAULT_URL) {
            val intent = Intent(context, WebViewActivity::class.java).apply {
                putExtra(EXTRA_URL, url)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        // Enable back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url = intent.getStringExtra(EXTRA_URL) ?: DEFAULT_URL

        webView = findViewById(R.id.webView)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }

    // Handle action bar back button
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // Handle device back button
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
