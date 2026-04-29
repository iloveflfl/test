# Run this in the project root after installing JDK 17, Android SDK command-line tools, and Gradle.
# Example:
#   powershell -ExecutionPolicy Bypass -File scripts\build_debug_no_studio_windows.ps1

$ErrorActionPreference = "Stop"
Write-Host "Installing required Android SDK packages..."
sdkmanager "platforms;android-35" "build-tools;35.0.0" "platform-tools"
Write-Host "Building debug APK..."
gradle :app:assembleDebug --stacktrace
Write-Host "Done. APK: app\build\outputs\apk\debug\app-debug.apk"
