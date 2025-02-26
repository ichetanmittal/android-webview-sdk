package com.example.android_sdk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun launch(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Enable back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"

        // Add a button to go back to WebView
        findViewById<Button>(R.id.backToWebViewButton).setOnClickListener {
            WebViewActivity.launch(this)
        }
    }

    // Handle action bar back button
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
