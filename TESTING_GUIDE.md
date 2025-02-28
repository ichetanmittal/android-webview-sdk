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

## Step 3: Use the SDK in Your Test Application

Add code to launch the WebView:

```kotlin
import com.example.android_sdk.WebViewActivity

// In your activity or fragment:

// Launch with default URL (https://elaborate-panda-b62f13.netlify.app/)
WebViewActivity.launch(context)

// Or launch with a custom URL
WebViewActivity.launch(context, "https://your-url.com")
```

## Step 4: Test JavaScript Bridge Functionality

To test the JavaScript bridge, you need a web page with JavaScript that calls the bridge methods:

```html
<!DOCTYPE html>
<html>
<head>
    <title>WebView SDK Test</title>
</head>
<body>
    <h1>WebView SDK Test Page</h1>
    
    <button onclick="showToast()">Show Toast</button>
    <button onclick="navigateToSettings()">Go to Settings</button>
    <button onclick="navigateToProfile()">Go to Profile</button>
    
    <script>
        function showToast() {
            AndroidBridge.showToast("Hello from JavaScript!");
        }
        
        function navigateToSettings() {
            AndroidBridge.navigateTo("settings");
        }
        
        function navigateToProfile() {
            AndroidBridge.navigateTo("profile");
        }
    </script>
</body>
</html>
```

You can host this page on a server or use a local HTML file.

## Step 5: Test Back Button Navigation

1. Navigate to several pages within the WebView
2. Press the back button to ensure proper navigation within the WebView history
3. When the WebView history is exhausted, the back button should close the WebView activity
