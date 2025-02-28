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
