package com.feelfreetocode.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView view = findViewById(R.id.icon);

        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(100)
                .playOn(findViewById(R.id.icon));
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(100)
                .playOn(findViewById(R.id.title));
    }
}
