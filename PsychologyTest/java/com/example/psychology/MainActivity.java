package com.example.psychology;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// onCreate 위해 해도됨 확인차 여기


public class MainActivity extends Activity {
    Button b1, b2, b3, b4;

    class MyListener implements View.OnClickListener {                  // 내부 클래스 쓰는 이유 = Activity 상속 받아 startActivity 사용 위해
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.b1:
                    //명시적 화면전환
                    Intent i = new Intent(MainActivity.this, SecondActivity.class);      // this만 쓰면 MyListener를 가리키기 때문에 에러 -> MainActivity.this
                    startActivity(i);                                                                  // 안드로이드에서 UI 호출할 때 자신의 Activity 클래스 던져줘야함
                    break;
                case R.id.b2:
                    break;
                case R.id.b3:
                    break;
                case R.id.b4:
                    break;

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {                                // 콜백 메소드 - 시스템이 호출.. 내가 호출 X       - 간단... 버튼 클릭은 복잡
        Toast.makeText(this, "onTouch", Toast.LENGTH_SHORT).show();

        //명시적 화면전환
//        Intent i = new Intent(this, SecondActivity.class);            // MainActivity, SecondActivity 는 누가 new 해주는가? --> 시스템이!
//        startActivity( i ); // 유지보수 위해 바로 SecondActivity X       // => manifests 파일에서 설정해줘야함!

        // 암시적인 화면전환
//        Uri number = Uri.parse("tel:02112119");
//        Intent dial = new Intent(Intent.ACTION_DIAL, number); // 다이얼 화면 호출
//        startActivity(dial);

        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);  // (1) 화면 설정, (2) res/layout mainactivity 자원 객체화

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);

        MyListener m = new MyListener();
        b1.setOnClickListener( m );
        b2.setOnClickListener( m );
        b3.setOnClickListener( m );
        b4.setOnClickListener( m );
    }
}