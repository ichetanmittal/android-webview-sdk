package com.example.android_sdk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun launch(context: Context) {
            val intent = Intent(context, ProfileActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Enable back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"

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
