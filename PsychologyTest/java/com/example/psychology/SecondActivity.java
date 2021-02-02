package com.example.psychology;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity { // 화면 기능 상속
    TextView t;
    Button se1, se2;

    String [] q = {"1. a", "2. b", "3. c", "4. d", "5. e"};
    int number = 0; // 현재 질문 index
    int score = 0; // 변태 지수

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // YES, NO 각각에 대한 점수 계산
            if ( view.getId() == R.id.se1 ){    //"YES" 버튼 선택
                score = score + 10;
            } else if ( view.getId() == R.id.se2 ){      //"NO" 버튼 선택
                score = score + 5;
            }
            number = number + 1;
            if ( number == q.length ) { // 더 보여줄 질문이 없다면 결과 화면으로
                Intent i = new Intent(SecondActivity.this, ResultActivity.class);
                // 화면 간 데이터 전달 - 보통 거의 문자열로 전달

                i.putExtra("score", score+"");  //문자열로 바꿔서 넘겨줌.
                startActivity(i);
            } else {    // 더 보여줄 질문이 있다면 다음 질문 보여주기
                t.setText(q[number]);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity);

        t = findViewById(R.id.textview);
        t.setText( q[number] );

        se1 = findViewById(R.id.se1);
        se1.setText("YES");
        se2 = findViewById(R.id.se2);
        se2.setText("NO");

        MyListener m = new MyListener();
        se1.setOnClickListener(m);
        se2.setOnClickListener(m);
    }

}
