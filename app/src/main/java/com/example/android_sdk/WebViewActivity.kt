package com.example.android_sdk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    companion object {
        private const val EXTRA_URL = "extra_url"
        private const val DEFAULT_URL = "https://elaborate-panda-b62f13.netlify.app/"

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
        supportActionBar?.title = "Web Content"

        val url = intent.getStringExtra(EXTRA_URL) ?: DEFAULT_URL

        webView = findViewById(R.id.webView)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }
        
        // Add JavaScript interface to allow web-to-native communication
        webView.addJavascriptInterface(WebAppInterface(this), "AndroidBridge")
        
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
    
    // JavaScript interface class to expose Android functions to JavaScript
    inner class WebAppInterface(private val context: Context) {
        
        @JavascriptInterface
        fun navigateTo(screenName: String) {
            runOnUiThread {
                when (screenName) {
                    "home" -> {
                        // Navigate to home/main activity
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                    "settings" -> {
                        // Navigate to settings activity
                        SettingsActivity.launch(context)
                    }
                    /*
                    "profile" -> {
                        // Navigate to profile activity
                        ProfileActivity.launch(context)
                    }
                    */
                    else -> {
                        Toast.makeText(context, "Unknown screen: $screenName", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        
        @JavascriptInterface
        fun navigateToAppScreen(className: String) {
            runOnUiThread {
                try {
                    // Try to find the class in the app's package
                    val packageName = context.applicationContext.packageName
                    val activityClass = Class.forName("$packageName.$className")
                    val intent = Intent(context, activityClass)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Error navigating to $className: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        @JavascriptInterface
        fun showToast(message: String) {
            runOnUiThread {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
