package com.example.activitylifecycle;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

public class MainActivity extends Activity {

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {          // 일시적 데이터 저장..         // onCreate의 파라미터와 관련
        super.onSaveInstanceState(outState);
        outState.putString("name", "김길동");          // 파일이 아닌 메모리에 일시적으로 데이터 저장 (기간은 자동.. 시스템이 정함.)
        Log.d("####################", "onSaveInstanceState");   // 홈버튼 누르면 호출됨. (강제 종료 대비 - 데이터 저장)
    }

    @Override	// UI 자원할당
    protected void onCreate(Bundle savedInstanceState) {    // 강제 종료 후 다시 시작할 때 위 메소드에서 저장되어있던 데이터 받음.
        super.onCreate(savedInstanceState);
        if ( savedInstanceState != null ) {
            String name = savedInstanceState.getString("name");     // 웹툰 어디까지 본지 같은거 (지금은 영구저장하긴함)
        }

        //Camera c = Camera.open();           //default 생성자라 new Camera() 못함. - singleton pattern (Ctrl+클릭)
        //c.release();                        // 자원 해제 (원래 자바는 new 반대.. 자원해제 없지만, 카레마는 중요한 자원이라 자원해제되는 메소드 제공됨)

        setContentView(R.layout.activity_main);
        Log.d("####################", "onCreate");   // Logcat에서 tag로 검색가능
    }

    @Override
    protected void onStart() {		// UI 이외 자원 할당
        super.onStart();
        Log.d("####################", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("####################", "onRestart");
    }

    @Override
    protected void onResume() {	// 로직 스타트(Thread Start)
        super.onResume();
        Log.d("####################", "onResume");
    }

    @Override
    protected void onPause() {          // 로직 정지, 영구적 데이터 저장(파일, DB에 저장)
        super.onPause();
        Log.d("####################", "onPause");
    }

    @Override
    protected void onStop() {       // UI 이외의 자원해제
        super.onStop();
        Log.d("####################", "onStop");
    }

    @Override
    protected void onDestroy() {            // 데코레이션
        super.onDestroy();
        Log.d("####################", "onDestroy");
    }

    //가로 모드 전환하면, 세로 창 destroy하고 가로 창을 새로 create함. --> 데이터 다 날라감. --> 지원X manifest파일
    // android:screenOrientation="portrait"
}
