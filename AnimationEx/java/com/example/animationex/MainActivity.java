package com.example.animationex;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
    LinearLayout layout;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new LinearLayout(this);
        b1 = new Button(this);
        b1.setText("b1");
        b2 = new Button(this);
        b2.setText("b2");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation a = AnimationUtils.loadAnimation(MainActivity.this, R.anim.total);
                a.setFillAfter(true);   // 애니메이션 후 변화상태 유지
                    //b2.startAnimation(a); // 버튼 애니메이션
                a.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {       // 애니메이션 끝나면 토스트
                        Toast.makeText(MainActivity.this, "END", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


                layout.startAnimation(a);   // 레이아웃 전체 애니메이션
            }
        });

        layout.addView(b1);
        layout.addView(b2);
        setContentView( layout );
    }
}

// 버튼 이동한 자리에 터치되도록하려면 res/animator 써야함