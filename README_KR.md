# Floating GIF Overlay Native

이 프로젝트는 MIT App Inventor 순정 컴포넌트로는 불가능한 기능을 네이티브 Android로 구현한 샘플입니다.

## 구현된 기능

- `SYSTEM_ALERT_WINDOW` 권한 요청 화면 이동
- `ForegroundService` 유지
- `WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY` 기반 GIF 오버레이
- 홈 버튼을 눌러도 GIF가 홈 화면/다른 앱 위에 남음
- 오버레이 드래그 이동
- 오버레이 우상단 × 버튼으로 종료
- 잠금화면 위 GIF Activity 테스트 버튼

## 실행 방법

1. Android Studio에서 이 폴더를 엽니다.
2. Gradle Sync를 진행합니다.
3. 휴대폰에서 USB 디버깅을 켜고 Run 합니다.
4. 앱 실행 후 `1. 다른 앱 위에 표시 권한 열기`를 누릅니다.
5. 설정에서 `Floating GIF Overlay`의 `다른 앱 위에 표시`를 허용합니다.
6. 앱으로 돌아와 `2. GIF 오버레이 시작`을 누릅니다.
7. 홈 버튼을 누르면 GIF가 홈 화면 위에 남아 있어야 합니다.

## 중요한 한계

- 화면이 완전히 꺼진 검은 상태에서는 앱 UI가 보이지 않습니다. 이건 Android 시스템 구조상 일반 앱이 할 수 없습니다.
- 서비스는 살아 있고, 화면을 다시 켜면 오버레이가 유지됩니다.
- 잠금화면 위 표시는 `Activity.setShowWhenLocked(true)` 방식으로 별도 구현되어 있습니다. 작은 버블 오버레이와는 별개입니다.
- 제조사 배터리 제한이 강한 기기에서는 오버레이 서비스가 꺼질 수 있습니다.

## MIT App Inventor만으로 안 되는 이유

MIT App Inventor 순정 컴포넌트에는 아래 기능이 없습니다.

- AndroidManifest에 `SYSTEM_ALERT_WINDOW`를 정확히 선언하고 제어
- `WindowManager.addView()`로 앱 외부 시스템 창 생성
- 백그라운드에서 살아있는 Foreground Service 생성
- 앱 밖에 남는 실제 이미지/GIF View 생성

App Inventor의 `Notifier.ShowAlert`는 Toast라서 홈/잠금화면에 잠깐 보일 수 있지만, 이미지/GIF를 계속 띄우는 실제 오버레이가 아닙니다.
