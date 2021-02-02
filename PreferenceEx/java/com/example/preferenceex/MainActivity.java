package com.example.preferenceex;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         Preference : 디바이스 내 저장 - (AVD로만 테스트 가능)
         파일 확인 -> 오른쪽 아래 Device File Exploler - Data/Data/com.example.preferenceex/shared_prefs
         */

        // preference 사용한 데이터 저장
        SharedPreferences settings = getSharedPreferences("test", 0);   // test 파일 가져오기 (없으면 새로 만듦.)
        SharedPreferences.Editor editor = settings.edit();  // 파일 수정하기 위한 editor
        editor.putString("age", "30");  // 바로 쓰지 않고 버퍼에 담아둠.
        editor.putString("name", "김해윙");
        editor.commit();    // 파일에 쓰러감.

        // preference 사용한 데이터 읽기
        SharedPreferences r = getSharedPreferences("test", 0);
        String result = r.getString("age", "20"); // age 값 가져옴. (값 없을 시, default 20)
        Toast.makeText(this, "age == " + result, Toast.LENGTH_SHORT).show();
    }
}