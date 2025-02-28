# Android WebView SDK

This SDK provides a simple way to embed a WebView component in your Android application with JavaScript bridge capabilities for cross-navigation between web and native components.

## Features
- Easy to integrate WebView component
- Configurable URL loading
- Back button navigation support
- JavaScript enabled by default
- Wide viewport support
- JavaScript bridge for web-to-native communication
- Cross-navigation between web content and native activities

## Installation

Add the following to your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        // ... other repositories
        maven {
            url = uri("path/to/local/maven/repo") // Or your repository URL
        }
    }
}
```

Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.example.android_sdk:webview-sdk:1.0.0")
}
```

## Usage

### Basic Usage

```kotlin
// Launch with default URL (https://elaborate-panda-b62f13.netlify.app/)
WebViewActivity.launch(context)

// Or launch with a custom URL
WebViewActivity.launch(context, "https://your-url.com")
```

### JavaScript Bridge

The SDK provides a JavaScript bridge that allows web content to communicate with your native Android application. This enables:

- Navigation from web content to native activities
- Passing data between web and native components
- Triggering native functionality from web content

Example of navigating to a native activity from JavaScript:

```javascript
// In your web application
if (window.AndroidBridge) {
    window.AndroidBridge.navigateToAppScreen("YourActivityName");
}
```

## Testing

For detailed instructions on how to test the SDK in your application, please refer to the [Testing Guide](TESTING_GUIDE.md). The guide includes:

- Step-by-step instructions for building the SDK
- Setting up a test application
- Testing basic WebView functionality
- Testing cross-navigation between web and native components
- Troubleshooting common issues

## Requirements
- Minimum SDK version: 24
- Compile SDK version: 35
- Internet permission in your app's AndroidManifest.xml

## License
[Your License]
