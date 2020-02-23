package com.feelfreetocode.bottomsheetsampleexample;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View bottumSheet = findViewById(R.id.bottomSheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottumSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setPeekHeight(0);

        View signupSheet = findViewById(R.id.bottomSheetsignup);
        final BottomSheetBehavior signupSheetBehavior = BottomSheetBehavior.from(signupSheet);
        signupSheetBehavior.setHideable(true);
        signupSheetBehavior.setPeekHeight(0);


        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                signupSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.e("StateChanged" , "Collapesd");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.e("StateChanged" , "Dragging");
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.e("StateChanged" , "Expanded");
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                Log.e("StateChanged" , "Slidig");
            }
        });



    }
}
