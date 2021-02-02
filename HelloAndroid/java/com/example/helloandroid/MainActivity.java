package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView tv;
    /*
        TextView tv = new TextView(this);

        멤버 변수 위치 --> 실행하면 error !! (Java SE(PC용) -> 정책 문제)
        => 초기화를 onCreate 안에 넣으면 해결

        - 선언은 멤버 변수 위치에 가능
        - 자원 할당은 onCreate 메소드 안에서만 해야 함.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 고전적 방법
//        tv = new TextView(this);
//        Button b = new Button(this);
//        b.setText("Button");
//        b.setOnClickListener(new View.OnClickListener() { // 익명 내부 클래스
//            @Override
//            public void onClick(View view) {
//                tv.setText("abc");
//            }
//        });
//        tv.setText("hello");
//        LinearLayout layout = new LinearLayout(this);
//        layout.addView(b);
//        layout.addView(tv);
//        setContentView( layout );

        // 권장되는 방법 - 화면 구성 후 버튼 클릭 이벤트 처리
        setContentView( R.layout.mainactivity ); // R == res 폴더
        /*
            1. 괄호 안의 activity를 사용하겠다
            2. 그 activity를 메모리에 올려주겠다.(java에서의 new 역할)
                --> button 사용하기 전에 실행해야됨.
         */
        Button b = findViewById( R.id.button );
        TextView tv = findViewById( R.id.textView);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("AAA");
            }
        });

    }
}