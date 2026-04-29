package com.example.floatinggifoverlay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshStatus();
    }

    private void buildUi() {
        int pad = dp(this, 18);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(pad, pad, pad, pad);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(Color.rgb(250, 248, 255));

        TextView title = new TextView(this);
        title.setText("Floating GIF Overlay");
        title.setTextSize(24);
        title.setTextColor(Color.rgb(40, 35, 50));
        title.setGravity(Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(-1, -2));

        TextView desc = new TextView(this);
        desc.setText("진짜 홈 화면/다른 앱 위에 남는 GIF 오버레이 테스트 앱입니다.\n1) 권한 허용 → 2) 오버레이 시작 → 3) 홈 버튼으로 나가기");
        desc.setTextSize(15);
        desc.setTextColor(Color.rgb(70, 65, 85));
        desc.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams descLp = new LinearLayout.LayoutParams(-1, -2);
        descLp.setMargins(0, dp(this, 12), 0, dp(this, 20));
        root.addView(desc, descLp);

        status = new TextView(this);
        status.setTextSize(14);
        status.setGravity(Gravity.CENTER);
        root.addView(status, new LinearLayout.LayoutParams(-1, -2));

        Button perm = button("1. 다른 앱 위에 표시 권한 열기");
        perm.setOnClickListener(v -> openOverlayPermission());
        root.addView(perm, btnLp());

        Button start = button("2. GIF 오버레이 시작");
        start.setOnClickListener(v -> startOverlay());
        root.addView(start, btnLp());

        Button stop = button("3. GIF 오버레이 종료");
        stop.setOnClickListener(v -> stopService(new Intent(this, FloatingOverlayService.class)));
        root.addView(stop, btnLp());

        Button lock = button("잠금화면 위 GIF 화면 테스트");
        lock.setOnClickListener(v -> startActivity(new Intent(this, LockscreenGifActivity.class)));
        root.addView(lock, btnLp());

        TextView note = new TextView(this);
        note.setText("참고: 화면이 완전히 꺼진 검은 상태에서는 어떤 앱 UI도 보이지 않습니다. 대신 서비스는 살아 있고, 화면을 다시 켜면 오버레이가 유지됩니다.");
        note.setTextSize(13);
        note.setTextColor(Color.rgb(90, 80, 100));
        note.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams noteLp = new LinearLayout.LayoutParams(-1, -2);
        noteLp.setMargins(0, dp(this, 18), 0, 0);
        root.addView(note, noteLp);

        setContentView(root);
    }

    private Button button(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setAllCaps(false);
        b.setTextSize(16);
        return b;
    }

    private LinearLayout.LayoutParams btnLp() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, dp(this, 52));
        lp.setMargins(0, dp(this, 12), 0, 0);
        return lp;
    }

    private void refreshStatus() {
        status.setText(canDrawOverlays()
                ? "권한 상태: 허용됨 ✅"
                : "권한 상태: 아직 허용 안 됨 ❌");
        status.setTextColor(canDrawOverlays() ? Color.rgb(0, 110, 70) : Color.rgb(180, 40, 40));
    }

    private boolean canDrawOverlays() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this);
    }

    private void openOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } else {
            Toast.makeText(this, "이 Android 버전은 별도 오버레이 권한 설정이 필요 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startOverlay() {
        if (!canDrawOverlays()) {
            Toast.makeText(this, "먼저 '다른 앱 위에 표시' 권한을 켜야 합니다.", Toast.LENGTH_LONG).show();
            openOverlayPermission();
            return;
        }
        Intent intent = new Intent(this, FloatingOverlayService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        Toast.makeText(this, "오버레이 시작됨. 홈 버튼을 눌러 확인하세요.", Toast.LENGTH_LONG).show();
    }

    static int dp(Context c, int value) {
        return (int) (value * c.getResources().getDisplayMetrics().density + 0.5f);
    }
}
