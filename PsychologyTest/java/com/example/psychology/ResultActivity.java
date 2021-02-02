package com.example.psychology;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 이전 화면에서 전달된 데이터 가져오기
        Intent i = getIntent();
        String result = i.getStringExtra("score");
        // i.getIntExtra("score", 0);

        // 결과 확인
        int score = Integer.parseInt(result);   //문자열 -> int
        int r = score / 10;
        switch ( r ) {
            case 5:
            case 4:
                result = "A";
                break;
            case 3:
                result = "B";
                break;
            case 2:
                result = "C";
                break;
            case 1:
                result = "D";
                break;
        }

        TextView t = new TextView(this);
        t.setText("Result: " + result );
        setContentView(t);

    }
}
