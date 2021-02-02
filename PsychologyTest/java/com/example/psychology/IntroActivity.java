package com.example.psychology;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class IntroActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundResource(R.drawable.intro);
        setContentView(layout); // 착각 ㄴㄴ, 지금 당장 화면에 보여주는 거 아님! 세팅만 해놔라 이거임. -> 쓰레드 없이는 인트로X

        new Thread(){   //익명 내부 클래스 - 1초 기다렸다가 화면 전환하기 위해
            public void run() {
                // 명시적인 화면 전환
                try {
                    Thread.sleep(1000); // 1초 대기.. 예외처리 무조건 해줘야하는 메소드
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish(); //현재 Activity 종료
                Intent i = new Intent(IntroActivity.this, MainActivity.class);    //클래스라서 this만 하면 에러 -> IntroActivity.this
                startActivity(i);
            }
        }.start();

    }
}
//intro 써야할 때 - 고객 정보를 가져오며 시간이 걸릴 때 사용.
//                 - Thread() 안에서 가져오는 동작 시행하면 됨.