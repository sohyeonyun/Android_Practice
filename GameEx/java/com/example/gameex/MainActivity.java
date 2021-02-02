package com.example.gameex;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

class Unit {                    // 변수 자체에 접근 못하고 메소드를 통해 접근 가능 - 유지보수 굳
    int x, y;
    Bitmap image;
    int ax, ay;
    int health;
}

public class MainActivity extends Activity {

    int score = 0;  // 게임 점수 기록
    int unit_count = 100;
    // 여러 개의 Unit 을 저장하기 위한 공간
    ArrayList total = new ArrayList();

    MyView m;
    Bitmap image;
    int x, y;   // 이미지의 좌표값
    int ax = 5;    // x 축 가속도
    int ay = 5;    // y 축 가속도

    int width = 480;    //현재 핸드폰 해상도
    int height = 800;


    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {   //callback 메소드 - 간접적으로 호출해야함
            // Main Thread에 부탁할 화면 갱신 작업을 코딩

            for ( int i = 0 ; i < unit_count ; i++) {
                Unit u = (Unit) total.get(i);

                if ( u.x > width  || u.x < 0) {
                    u.ax = -u.ax;
                }
                if ( u.y > height || u.y < 0 ) {
                    u.ay = -u.ay;
                }

                u.x = u.x + u.ax;
                u.y = u.y + u.ay;

            }
            // 100개 다 확인 후 새로 그리기
            m.invalidate();
            sendEmptyMessageDelayed(0, 20);    // 자기 자신 부름으로써 재귀함수처럼 작동
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) { //callback method
        int x2 = (int) event.getX();
        int y2 = (int) event.getY();

        for ( int i = unit_count - 1 ; i >= 0 ; i--) {
            Unit u = (Unit) total.get(i);
            if ( u.health > 0 ) {
                double b = Math.sqrt(Math.pow(u.x - x2, 2) + Math.pow(u.y - y2, 2));
                if (b < 150) {
                    u.health = u.health - 100;
                    switch (u.health / 100) {
                        case 4:
                        case 3:
                            u.image = BitmapFactory.decodeResource(getResources(), R.drawable.bug1);
                            break;
                        case 2:
                        case 1:
                            u.image = BitmapFactory.decodeResource(getResources(), R.drawable.bug2);
                            break;
                        default:
                            score += 1;
                    }
                    break;
                    //Toast.makeText(this, "touch", Toast.LENGTH_SHORT).show();
                }
            }
        }


//        for(int i = 0; i < 10 ; i++){
//            try{
//                Thread.sleep(500);
//            } catch ( InterruptedException e ){
//                e.printStackTrace();
//            }
//            x += 30;
//            y += 30;
//            m.invalidate();
//        }

        return super.onTouchEvent(event);
    }

    // 내부 클래스 -> 외부 클래스의 멤버 변수 자원 공유 위해
    // 제공하는 뷰가 아닌, 나만의 뷰 만들어야함
    class MyView extends View {             // View 클래스가 추상 클래스(일부 메소드가 구현X) --> 우리가 만들어야함.

        public MyView(Context context) {    // 화면에 관련된 곳에서 this 해준것처럼(context)
            super(context);
            // 안드로이드에서 res/drawable 이미지 Bitmap type으로 가져오기
            image = BitmapFactory.decodeResource(getResources(), R.drawable.bug);
            MyHandler handler = new MyHandler();
            handler.sendEmptyMessage(0);    // 0 : 어디서 호출되는지 확인용 == msg.what

            for( int i = 0 ; i < unit_count ; i++) {
                Unit u = new Unit();
                u.x = (int)( Math.random() * width );    // 0.0 <= Math.random() < 1.0
                u.y = (int)( Math.random() * height);
                u.ax = (int)( Math.random() * 10) + 1;
                u.ay = (int)( Math.random() * 10) + 1;
                u.health = 500;
                u.image = image;
                total.add(u);   // Unit 저장
            }
        }

        @Override   // Alt + Insert
        protected void onDraw(Canvas canvas) {  //Callback 메소드 MyView 호출시 이것도 같이 호출됨
            // paint(글자 크기, 색깔 클래스)
            Paint p = new Paint();
            p.setColor(Color.GREEN);
            p.setTextSize(80);

            // canvas : 그림 관련 모든 것
            for ( int i = 0 ; i < unit_count ; i++) {
                Unit u = (Unit) total.get(i);   // 그냥 꺼내면 Object 타입
                if ( u.health > 0 ) {
                    canvas.drawBitmap(u.image, u.x, u.y, null);   // (x, y)에 image 그림
                }
            }
            canvas.drawText("score = " + score, width - 550, 200, p);   // 마지막 그림이 젤 위에

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 알림창(Notification Bar) 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 핸드폰 해상도 가져오기
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        width = display.getWidth(); //취소선 == 하위버전
        height = display.getHeight();

        m = new MyView(this);
        m.setBackgroundResource(R.drawable.back);
        setContentView( m );
    }
}




//class Unit {                    // 변수 자체에 접근 못하고 메소드를 통해 접근 가능 - 유지보수 굳
//    private int x, y;
//    private Bitmap image;
//    private int ax, ay;
//    private int health;
//
//    // Alt + Inset -> Constructor
//    public Unit(int x, int y, Bitmap image, int ax, int ay, int health) {
//        this.x = x;
//        this.y = y;
//        this.image = image;
//        this.ax = ax;
//        this.ay = ay;
//        this.health = health;
//    }
//
//    // Alt + Inset -> getter and setter
//    public int getX() {
//        return x;
//    }
//
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }
//
//    public Bitmap getImage() {
//        return image;
//    }
//
//    public void setImage(Bitmap image) {
//        this.image = image;
//    }
//
//    public int getAx() {
//        return ax;
//    }
//
//    public void setAx(int ax) {
//        this.ax = ax;
//    }
//
//    public int getAy() {
//        return ay;
//    }
//
//    public void setAy(int ay) {
//        this.ay = ay;
//    }
//
//    public int getHealth() {
//        return health;
//    }
//
//    public void setHealth(int health) {
//        this.health = health;
//    }
//}