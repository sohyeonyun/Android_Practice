package com.example.memoapp;

import android.app.Activity;
import android.os.Bundle;

public class SubActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {  // @ : 지능형 주석 - @Override = 오버라이딩된 메소드 검증해줌.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity);


    }



}
