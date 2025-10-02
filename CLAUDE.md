# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Android calculator application written in Kotlin. The app uses Fragments to display two different calculator interfaces (Calc1 and Calc2) that users can switch between using buttons in the MainActivity.

**Package**: `com.example.calculate`
**Language**: Kotlin
**Android SDK**: minSdk 24, targetSdk 36, compileSdk 36
**Java Version**: 11

## Build Commands

```bash
# Build the project
./gradlew build

# Assemble debug APK
./gradlew assembleDebug

# Assemble release APK
./gradlew assembleRelease

# Clean build artifacts
./gradlew clean

# Install debug build on connected device
./gradlew installDebug
```

## Testing Commands

```bash
# Run all unit tests
./gradlew test

# Run debug unit tests only
./gradlew testDebugUnitTest

# Run instrumented tests on connected device
./gradlew connectedAndroidTest

# Run debug instrumented tests
./gradlew connectedDebugAndroidTest
```

## Code Quality Commands

```bash
# Run lint checks
./gradlew lint

# Run lint and apply safe fixes
./gradlew lintFix

# Run lint on debug variant
./gradlew lintDebug
```

## Architecture

### Fragment-Based UI
- **MainActivity**: The main entry point that hosts a FrameLayout container for fragments
- **Calc1 Fragment**: First calculator UI (uses `R.layout.calc1`)
- **Calc2 Fragment**: Second calculator UI (uses `R.layout.calc2`)
- Users switch between calculators using two buttons in the MainActivity

### Fragment Management
- MainActivity maintains an ArrayList of Fragment instances
- FragmentManager handles fragment transactions with `.replace()` to swap between calculator UIs
- The FrameLayout with id `R.id.FL` serves as the container for fragment replacement

### Layout Structure
- `activity_main.xml`: Contains two toggle buttons and a FrameLayout container
- `calc1.xml`: First calculator layout with TableLayout containing number/operator buttons
- `calc2.xml`: Second calculator layout (similar structure to calc1)
- Both calculator layouts use TableRow arrangements for button grids

## Project Structure

```
app/src/main/
├── java/com/example/calculate/
│   ├── MainActivity.kt          # Main activity with fragment switching
│   ├── ui/theme/               # Compose theme files (Color, Theme, Type)
│   └── [Calc1, Calc2 classes in MainActivity.kt]
├── res/
│   └── layout/
│       ├── activity_main.xml    # Main activity layout
│       ├── calc1.xml           # First calculator UI
│       └── calc2.xml           # Second calculator UI
└── AndroidManifest.xml
```

## Dependencies

The project uses:
- AndroidX Core KTX
- AndroidX Lifecycle Runtime KTX
- AndroidX AppCompat
- Jetpack Compose (Activity Compose, Material3, UI components)
- JUnit for unit testing
- Espresso for instrumented testing
