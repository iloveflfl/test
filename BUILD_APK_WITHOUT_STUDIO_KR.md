# Android Studio 없이 APK 만들기

이 프로젝트는 MIT App Inventor가 아니라 네이티브 Android 프로젝트입니다. Android Studio를 설치하지 않아도 아래 두 방법 중 하나로 APK를 만들 수 있습니다.

## 방법 1: GitHub Actions로 온라인 빌드

1. GitHub에서 새 repository를 만듭니다.
2. 이 폴더의 모든 파일을 업로드합니다. `.github/workflows/build-apk.yml` 파일도 반드시 포함되어야 합니다.
3. repository의 **Actions** 탭으로 갑니다.
4. **Build Debug APK** 워크플로를 선택합니다.
5. **Run workflow**를 누릅니다.
6. 빌드가 끝나면 아래쪽 **Artifacts**에서 `FloatingGifOverlay-debug-apk`를 다운로드합니다.
7. 압축을 풀면 `app-debug.apk`가 나옵니다.

이 APK는 debug 서명 APK입니다. 휴대폰에 설치할 수 있지만, Play Store 배포용은 아닙니다.

## 방법 2: Windows에서 명령줄 빌드

Android Studio 전체 설치 대신 아래만 설치합니다.

- JDK 17
- Android SDK Command-line Tools
- Gradle 8.7 이상

필요 SDK 패키지:

```powershell
sdkmanager "platforms;android-35" "build-tools;35.0.0" "platform-tools"
```

프로젝트 폴더에서:

```powershell
gradle :app:assembleDebug
```

성공하면 APK 위치는 다음입니다.

```text
app\build\outputs\apk\debug\app-debug.apk
```

## APK 설치 후 해야 하는 것

1. 휴대폰에서 APK 설치
2. 앱 실행
3. `1. 다른 앱 위에 표시 권한 열기` 누르기
4. 설정에서 권한 허용
5. 앱으로 돌아와 `2. GIF 오버레이 시작` 누르기
6. 홈 버튼으로 나가서 GIF가 남는지 확인

## 한계

- 화면이 완전히 꺼진 상태에서는 GIF가 보이지 않습니다.
- 일부 제조사 기기는 배터리 최적화 때문에 서비스가 죽을 수 있습니다.
- Android 10 이상에서는 다른 앱 위 오버레이 권한을 사용자가 직접 켜야 합니다.
