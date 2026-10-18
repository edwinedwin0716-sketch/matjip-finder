package kr.nurseai.matjip;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

// 우리동네 맛·볼거리: 깃허브 페이지의 웹앱을 앱 화면으로 연다 (항상 최신 버전).
// 지도 앱 · 네이버 · 카카오 링크는 휴대폰의 앱/브라우저로 넘긴다.
public class MainActivity extends Activity {
    static final String HOME = "https://edwinedwin0716-sketch.github.io/matjip-finder/";
    static final String HOST = "edwinedwin0716-sketch.github.io";
    private WebView web;
    private String geoOrigin;
    private GeolocationPermissions.Callback geoCallback;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(10, 20, 48));
        getWindow().setNavigationBarColor(Color.rgb(10, 20, 48));
        web = new WebView(this);
        web.setBackgroundColor(Color.rgb(10, 20, 48));
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setGeolocationEnabled(true);
        s.setTextZoom(100);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        web.addJavascriptInterface(new Object() {
            @JavascriptInterface public void retry() { runOnUiThread(new Runnable() { public void run() { web.loadUrl(HOME); } }); }
        }, "Android");
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri u = Uri.parse(url);
                if (HOST.equals(u.getHost()) || url.startsWith("file:///android_asset/")) return false;
                openOutside(url);
                return true;
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError err) {
                if (req.isForMainFrame()) view.loadUrl("file:///android_asset/offline.html");
            }
        });
        web.setWebChromeClient(new WebChromeClient() {
            // 웹앱의 '현재 내 위치 사용' → 안드로이드 위치 권한을 물어본다
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    callback.invoke(origin, true, false);
                    return;
                }
                geoOrigin = origin; geoCallback = callback;
                requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 1);
            }
        });
        setContentView(web);
        if (state != null) web.restoreState(state); else web.loadUrl(HOME);
    }

    @Override
    public void onRequestPermissionsResult(int code, String[] perms, int[] results) {
        boolean ok = false;
        for (int r : results) if (r == PackageManager.PERMISSION_GRANTED) ok = true;
        if (geoCallback != null) { geoCallback.invoke(geoOrigin, ok, false); geoCallback = null; }
        if (!ok) Toast.makeText(this, "위치 권한이 없으면 주소로 검색해 주세요", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle out) { super.onSaveInstanceState(out); web.saveState(out); }

    @Override
    public void onBackPressed() {
        // 위치 선택 창이 열려 있으면 닫고, 아니면 앱을 닫는다
        web.evaluateJavascript("(function(){var m=document.getElementById('modal');if(m&&!m.classList.contains('hidden')){m.classList.add('hidden');return true}return false})()",
            new android.webkit.ValueCallback<String>() {
                @Override public void onReceiveValue(String v) { if (!"true".equals(v)) { if (web.canGoBack()) web.goBack(); else finish(); } }
            });
    }

    private void openOutside(String url) {
        try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))); }
        catch (Exception e) { Toast.makeText(this, "열 수 있는 앱이 없어요", Toast.LENGTH_SHORT).show(); }
    }
}
