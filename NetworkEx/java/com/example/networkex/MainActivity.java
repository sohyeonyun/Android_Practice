package com.example.networkex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

/*
    네트워크 통신
    1. manifest 파일에서 uses-permission 주기
    2. Thread 사용
    3. Handler 사용 (쓰레드 안에서 화면 갱신 불가하기 떄문)
 */

public class MainActivity extends Activity {

    TextView tv;
    byte[] bText = new byte[256];;  // 배열이라 여기서 할당 가능. TextView는 불가.

    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {   // 화면 갱신 코드 삽입
            tv.setText(new String(bText));
        }
    }
    MyHandler myHandler = new MyHandler();  // Handler는 Thread 안에서 객체화 불가.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv = new TextView(this);
        setContentView( tv );

        new Thread() {
            public void run() {

                try {
                    URL text = new URL("https://www.google.com");   // http 는 보안상 이슈로 X
                    InputStream isText = text.openStream();
//                    bText = new byte[256];
                    int readSize = isText.read(bText);
                    Log.i("Net", "readSize = " + readSize);
                    Log.i("Net", "bText = " + new String(bText));
//                    tv.setText(new String(bText));
                    myHandler.sendEmptyMessage(0);
                    isText.close();
                } catch (Exception e) {
                    Log.e("Net", "Error in network call", e);
                }

            }
        }.start();


    }
}