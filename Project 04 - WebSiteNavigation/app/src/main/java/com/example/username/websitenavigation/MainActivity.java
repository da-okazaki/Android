package com.example.username.websitenavigation;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    private ProgressBar progressBar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    webView.loadUrl("http://www.google.co.jp/");
                    return true;
                case R.id.navigation_dashboard:
                    webView.loadUrl("http://splax.net/");
                    return true;
                case R.id.navigation_notifications:
                    webView.loadUrl("http://flowersburger.com/");
                    return true;
                case R.id.navigation_back:
                    // 戻るボタンが押されたときの処理
                    webView.goBack();
                    return true;
                case R.id.navigation_forward:
                    // 進むボタンが押されたときの処理
                    webView.goForward();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        webView = (WebView) findViewById(R.id.webView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // 最初はプログレスバーを非表示
        progressBar.setVisibility(View.INVISIBLE);

        // タップしたときにブラウザを起動しない
        webView.setWebViewClient(new WebViewClient() {

            // Webページの読み込みが始まった
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            // Webページの読み込みが終わった
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        // JavaScript有効
        webView.getSettings().setJavaScriptEnabled(true);
    }

}
