package com.example.linearlayoutex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    Button b1, b2, b3;
    LinearLayout layout;

    // 클릭 감시용 내부 클래스 정의
    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button:
                    layout.setBackgroundResource(R.drawable.cat);
                    break;
                case R.id.button2:
                    layout.setBackgroundResource(R.drawable.cat2);
                    break;
                case R.id.button3:
                    layout.setBackgroundResource(R.drawable.cat3);
                    break;
            }

//            if (view.getId() == R.id.button){
//                layout.setBackgroundResource(R.drawable.cat); // ~~Resource(R.drawable.~~);
//            } else if (view.getId() == R.id.button2){
//                layout.setBackgroundResource(R.drawable.cat2);
//            } else if (view.getId() == R.id.button3){
//                layout.setBackgroundResource(R.drawable.cat3);
//            }
//

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        layout = findViewById(R.id.linear);

        // 리스너 사용 - 메모리에 올림 (객체화)
        MyListener m = new MyListener();
        b1.setOnClickListener(m);
        b2.setOnClickListener(m);
        b3.setOnClickListener(m);
    }
}