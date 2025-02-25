# Android WebView SDK

This SDK provides a simple way to embed a WebView component in your Android application.

## Features
- Easy to integrate WebView component
- Configurable URL loading
- Back button navigation support
- JavaScript enabled by default
- Wide viewport support

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

```kotlin
// Launch with default URL (https://xaults.com)
WebViewActivity.launch(context)

// Or launch with a custom URL
WebViewActivity.launch(context, "https://your-url.com")
```

## Requirements
- Minimum SDK version: 24
- Compile SDK version: 35

## License
[Your License]
