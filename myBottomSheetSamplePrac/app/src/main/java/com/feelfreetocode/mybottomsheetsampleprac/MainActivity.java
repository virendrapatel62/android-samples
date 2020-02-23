package com.feelfreetocode.mybottomsheetsampleprac;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View bottomSheet = findViewById(R.id.bottomsheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setPeekHeight(0);

        View bottomSheetsignup = findViewById(R.id.bottomsheetsignup);
        final BottomSheetBehavior bottomSheetBehavior2 = BottomSheetBehavior.from(bottomSheetsignup);
        bottomSheetBehavior2.setPeekHeight(0);
        bottomSheetBehavior2.setHideable(true);


        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });



    }
}
