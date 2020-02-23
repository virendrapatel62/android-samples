package com.booknet.booknet;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class webactivity extends AppCompatActivity {

    WebView webView;
    LinearLayout loading;
    LinearLayout splash;
    ProgressBar progressBar;


    public static final String userAgent = "Mozilla/5.0 (Linux; Android 7.0; SM-G930V Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webactivity);

        getWindow().setStatusBarColor(getResources().getColor(R.color.actionColor));
        if(isValid()) {
            loading = findViewById(R.id.loading);
            webView = findViewById(R.id.web);
            splash = findViewById(R.id.splash);
            progressBar = findViewById(R.id.progressBar);
            webView.setWebViewClient(new MyWebViewClient(loading, splash));

            webView.getSettings().setUserAgentString(userAgent);
            webView.loadUrl("https://booknet.in/");
            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if(newProgress < 50){
                        loading.setVisibility(LinearLayout.VISIBLE);
                    }else{
                        loading.setVisibility(LinearLayout.INVISIBLE);
                        splash.setVisibility(LinearLayout.GONE);
                    }
                    Log.d("message:", "onProgressChanged: " + newProgress);
                }
            });
            webView.getSettings().setJavaScriptEnabled(true);
        }else{
            Toast.makeText(this, "Application is Expired.. Make Payment to cont...", Toast.LENGTH_LONG).show();
        }




    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }


    public boolean isValid(){


        return true;
    }



}
