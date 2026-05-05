# FRUIT CANDY CRUSH CARZY

An Android match-3 fruit puzzle game with animated swaps, combo scoring, level progression, sound, haptics, Firebase services, and AdMob support.

FRUIT CANDY CRUSH CARZY is an Android match-3 fruit puzzle game built with Kotlin and Jetpack Compose. The game uses an 8 x 8 board, animated fruit swaps, combo scoring, level progression, sound effects, background music, vibration feedback, saved preferences, Firebase services, and AdMob banner ads.

## Features

- Match-3 gameplay with tap and swipe controls.
- 8 x 8 fruit board with automatic refill and gravity.
- Combo scoring, special fruit effects, level targets, move limits, and timers.
- High score and settings persistence with local app storage.
- Sound effects, music loops, and optional haptic feedback.
- Game over, level up, settings, start, and rate-app overlays.
- AdMob banner support with Google Mobile Ads initialization.
- Firebase Crashlytics and Performance Monitoring dependencies.

## Tech Stack

- Kotlin
- Android Gradle Plugin
- Jetpack Compose
- Material 3
- AndroidX Lifecycle and ViewModel
- Room, DataStore, and Coroutines
- Firebase Crashlytics and Firebase Performance
- Google Mobile Ads SDK
- Gradle Version Catalog

## Project Structure

```text
app/src/main/java/com/fruitcandycrushcarzy/APP/
  MainActivity.kt
  game/
    data/
    logic/
    model/
    util/
    viewmodel/
  ui/
    components/
    theme/
```

## Requirements

- Android Studio
- JDK 11 or newer
- Android SDK with compile SDK 36 support
- Gradle wrapper included in this repository

## Setup

1. Clone the repository.
2. Open the project in Android Studio.
3. Let Android Studio sync Gradle.
4. Confirm `local.properties` points to your local Android SDK. This file is intentionally ignored by git.
5. Add or update Firebase and AdMob configuration for your own app before production release.

## Build

From the project root:

```powershell
.\gradlew.bat assembleDebug
```

Run unit tests:

```powershell
.\gradlew.bat test
```

## Firebase and Ads Notes

The app includes Firebase and Google Mobile Ads dependencies. The current manifest and string resources use Google test AdMob IDs. Replace them with your own production IDs before publishing the app.

The repository includes `app/google-services.json` because the Android build is configured with the Google Services Gradle plugin. For a public repository, review this file and make sure it only contains configuration you are comfortable publishing.

## Package

```text
com.fruitcandycrushcarzy.APP
```

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.
