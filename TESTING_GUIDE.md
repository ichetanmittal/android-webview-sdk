# WebView SDK Testing Guide

This guide provides step-by-step instructions on how to test the WebView SDK in a test application.

## Step 1: Build the SDK

First, you need to build the SDK and make it available for your test application:

1. Navigate to the SDK project directory:
   ```bash
   cd /path/to/androidsdk
   ```

2. Publish to your local Maven repository:
   ```bash
   ./gradlew publishToMavenLocal
   ```

## Step 2: Set Up Test Application

Create a new Android application project to test the SDK:

1. Create a new Android application project in Android Studio.

2. Configure your `settings.gradle.kts` to include the necessary repositories:
   ```kotlin
   dependencyResolutionManagement {
       repositories {
           google()
           mavenCentral()
           // For local Maven repository
           mavenLocal()
       }
   }
   ```

3. Add the SDK dependency to your app's `build.gradle.kts`:
   ```kotlin
   dependencies {
       // Using Maven local repository
       implementation("com.example.android_sdk:webview-sdk:1.0.0")
   }
   ```

4. Add the Internet permission to your `AndroidManifest.xml`:
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   ```

5. If you encounter a manifest merger conflict due to the application theme, add the `tools:replace` attribute to the application element in your `AndroidManifest.xml`:
   ```xml
   <application
       ...
       android:theme="@style/Theme.YourAppTheme"
       tools:replace="android:theme">
       ...
   </application>
   ```

## Step 3: Use the SDK in Your Test Application

1. Create a button in your activity layout (`activity_main.xml`):
   ```xml
   <Button
       android:id="@+id/launch_webview_button"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:text="Launch WebView" />
   ```

2. Add code to launch the WebView in your activity (`MainActivity.kt`):
   ```kotlin
   import com.example.android_sdk.WebViewActivity

   // In your activity:
   val launchWebViewButton = findViewById<Button>(R.id.launch_webview_button)
   
   launchWebViewButton.setOnClickListener {
       // Launch WebView with the test URL
       WebViewActivity.launch(this, "https://elaborate-panda-b62f13.netlify.app/")
   }
   ```

## Step 4: Test Cross-Navigation from WebView to Native Activities

The WebView SDK supports navigating from web content back to native activities in your app. This demonstrates the JavaScript bridge functionality.

1. Create a native activity in your test app (`TestActivity.kt`):
   ```kotlin
   package com.example.testv2

   import android.os.Bundle
   import android.widget.Button
   import androidx.appcompat.app.AppCompatActivity

   class TestActivity : AppCompatActivity() {
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_test)
           
           // Set up back button
           val backButton = findViewById<Button>(R.id.back_button)
           backButton.setOnClickListener {
               finish()
           }
       }
   }
   ```

2. Create a layout for the test activity (`activity_test.xml`):
   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <androidx.constraintlayout.widget.ConstraintLayout 
       xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       xmlns:tools="http://schemas.android.com/tools"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       tools:context=".TestActivity">

       <TextView
           android:id="@+id/test_title"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="Test Activity"
           android:textSize="24sp"
           android:textStyle="bold"
           app:layout_constraintBottom_toBottomOf="parent"
           app:layout_constraintEnd_toEndOf="parent"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toTopOf="parent"
           app:layout_constraintVertical_bias="0.3" />

       <TextView
           android:id="@+id/test_description"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_marginTop="16dp"
           android:text="This is a native Android activity launched from the WebView"
           android:textAlignment="center"
           android:padding="16dp"
           app:layout_constraintEnd_toEndOf="parent"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toBottomOf="@+id/test_title" />

       <Button
           android:id="@+id/back_button"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_marginTop="32dp"
           android:text="Go Back"
           app:layout_constraintEnd_toEndOf="parent"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toBottomOf="@+id/test_description" />
   </androidx.constraintlayout.widget.ConstraintLayout>
   ```

3. Register the activity in your AndroidManifest.xml:
   ```xml
   <activity
       android:name=".TestActivity"
       android:exported="false"
       android:label="Test Activity" />
   ```

4. Add a direct navigation button to your MainActivity for comparison testing:
   
   In `activity_main.xml`:
   ```xml
   <Button
       android:id="@+id/go_to_test_button"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:text="Go to Test Page"
       app:layout_constraintTop_toBottomOf="@+id/launch_webview_button"
       app:layout_constraintStart_toStartOf="parent"
       app:layout_constraintEnd_toEndOf="parent" />
   ```
   
   In `MainActivity.kt` (make sure to add the Intent import):
   ```kotlin
   import android.content.Intent
   
   // Find the test page button
   val goToTestButton = findViewById<Button>(R.id.go_to_test_button)
   
   // Set click listener for test page button
   goToTestButton.setOnClickListener {
       // Navigate directly to the TestActivity
       val intent = Intent(this, TestActivity::class.java)
       startActivity(intent)
   }
   ```

5. Test both navigation paths:
   - Direct navigation: Click the "Go to Test Page" button in MainActivity
   - WebView navigation: Launch the WebView and click the "Go to Test Page" button in the web content

   Both should navigate to the same TestActivity, demonstrating the bridge between web and native components.

## Troubleshooting

### Common Issues

1. **Manifest Merger Failed**
   - If you see a manifest merger error related to the theme, make sure you've added `tools:replace="android:theme"` to your application element in AndroidManifest.xml.
   - Also ensure you've added the xmlns:tools declaration: `xmlns:tools="http://schemas.android.com/tools"`

2. **WebView Not Loading**
   - Verify that you've added the Internet permission in your AndroidManifest.xml.
   - Check that the URL is correct and accessible.
   - Make sure you're testing on a device with internet connectivity.

3. **JavaScript Bridge Not Working**
   - The bridge requires the URL to be loaded correctly in the WebView.
   - The hosted React app at https://elaborate-panda-b62f13.netlify.app/ is already configured to use the JavaScript bridge.
   - If you're using a custom URL, make sure your JavaScript is calling the bridge methods correctly.

4. **TestActivity Not Found**
   - Ensure that TestActivity is properly registered in your AndroidManifest.xml.
   - Check that the package name in TestActivity.kt matches your application's package name.
   - Verify that you've imported the correct classes in MainActivity.kt.
