package com.example.customviewex;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //(1) ListView 생성
        ListView listView = new ListView(this);

        //(2) ListView에 공급되는 데이터  --> 클래스
        ArrayList<IconTextItem> data = new ArrayList<IconTextItem>();   // <> 타입의 객체만 넣을 거야.
        IconTextItem d1 = new IconTextItem(R.drawable.carrot,
                "A",
                "B",
                "C"
        );
        IconTextItem d2 = new IconTextItem(R.drawable.carrot,
                "D",
                "E",
                "F"
        );
        data.add(d1);
        data.add(d2);


        //(3) ListView와 데이터를 연동해주기위한 Adapter 생성
        MyAdapter adapter = new MyAdapter(this, data);    // Context 넘겨줌.

        //(4) ListView에 Adapter 설정(연동)
        listView.setAdapter(adapter);

        setContentView(listView);
    }
}