package com.example.listviewex;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ListView 사용
        // (1) ListView 객체화
        ListView list = new ListView(this);
        // (2) ListView 에 공급될 데이터 정의
        
        String[] str = {"A", "B", "C"};
        // (3) ListView - Data 중계를 위한 Adapter 생성
        // i. ListView에 데이터 공급      ii. ListView 한 줄의 모양을 결정
        ArrayAdapter adapter = new ArrayAdapter(this,           // array 읽응 adapter
                R.layout.line,                          // 한 줄의 모양
                R.id.button,                            // 공급되는 데이터를 보여줄 위치 ( 밑과는 달리, 셋 중 하나 골라줘야함)
                str                                     // 곱급되는 데이터
                );
//        ArrayAdapter adapter = new ArrayAdapter(this,           // array 읽응 adapter
//                android.R.layout.simple_list_item_1,    // 한 줄의 모양
//                str                                     // 곱급되는 데이터
//        );
        // (4) Adapter를 ListView에 연동(설정)
        list.setAdapter(adapter);

        setContentView(list);
    }
}