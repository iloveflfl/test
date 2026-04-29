package com.example.floatinggifoverlay;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

public class LockscreenGifActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }

        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(0xCC000000);

        WebView webView = new WebView(this);
        webView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(false);
        settings.setAllowFileAccess(true);
        String html = "<html><head><style>html,body{margin:0;padding:0;background:transparent;height:100%;display:flex;align-items:center;justify-content:center;}" +
                "img{max-width:80vw;max-height:55vh;object-fit:contain;}</style></head>" +
                "<body><img src='file:///android_asset/popup.gif'></body></html>";
        webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
        root.addView(webView, new FrameLayout.LayoutParams(-1, -1));

        TextView close = new TextView(this);
        close.setText("닫기");
        close.setTextSize(18);
        close.setTextColor(Color.WHITE);
        close.setGravity(Gravity.CENTER);
        close.setBackgroundColor(0xAA222222);
        close.setOnClickListener(v -> finish());
        FrameLayout.LayoutParams closeLp = new FrameLayout.LayoutParams(MainActivity.dp(this, 120), MainActivity.dp(this, 48));
        closeLp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        closeLp.setMargins(0, 0, 0, MainActivity.dp(this, 48));
        root.addView(close, closeLp);

        root.setOnClickListener(v -> {});
        setContentView(root);
    }
}
