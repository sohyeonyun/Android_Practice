package com.example.memoapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

// Activity 상속받는 환경에서 ListView 화면에 보여주기
public class MainActivity extends Activity {

    SQLiteDatabase sd;
    ListView listView;
    Cursor c;


//    @Override
//    protected void onStop() {       // UI 이외의 자원 해제
//        super.onStop();
//        c.close();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {        // UI 자원만 할당
        super.onCreate(savedInstanceState);
        setContentView( R.layout.mainactivity );    // findViewById 보다 이전에 와야함.

        sd = openOrCreateDatabase("memo.db", 0, null);      // 얘는 apk에 포함안됨. 설치할 때 다 불러와야됨.. -> res에 넣으면 가능..
        // create table                                                                 //device file/data/data/com.example.sqliteex/databases/memo.db 생성
        String schema = "create table note (_id integer primary key autoincrement, "    //id : 값 안넣어줘도 자동증가
                + "title text not null, body text not null);" ;
        try {
            sd.execSQL(schema);
        } catch (Exception ignore){
            Log.d("######################", "테이블 생성 문제 발생!!!"); // d(debug), i(information), e(error) 기능은 같음.
        }

        // record insert
        ContentValues values = new ContentValues();                 // (key, value) 값 저장
        values.put("title", "First");
        values.put("body", "Hello");
        sd.insert("note", null, values);


        // ListView를 사용하여 SQLiteDatabase애서 읽어온 데이터 보여주기
        //(1) ListView 객체화
        listView = findViewById(R.id.list);
        //(2) ListView에 공급되는 데이터
        c = sd.query("note", null, null, null,              // UI 이외의 자원 할당 --> onStart()
                null, null, null);    // select * from note; 와 동일 기능
        //(3) (ListView - 데이터) 연동을 위한 Adapter           // (데이터타입)Adapter --> ListAdapter, CursorAdapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,                 // Ctrl + 함수 클릭
                R.layout.line, //한줄의 모양
                c,  //공급되는 데이터
                new String[]{"_id", "title"},   //from
                new int[]{R.id.index, R.id.title }   //to    - R.class 안에 int 로 정의됨.
                );
        //(4) ListView에 Adapter 적용
        listView.setAdapter(adapter);

        // ListView 한 줄 클릭에 대한 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // 한 줄 클릭시 실행되는 callback 메소드
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, "position = " + i, Toast.LENGTH_SHORT).show();

                // 선택된 메모 세부 내용을 보여주기 위한 SubActivity 호출
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                startActivity(intent);
            }
        });

        // ListView 한 줄 롱클릭에 대한 처리
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "LongClick", Toast.LENGTH_SHORT).show();
                return true;   // Click 중복 인식 방지 -> True(마무리)
            }
        });

        //c.close();    - (onCreate -> onResume -> 화면 호출)인데 커서를 먼저 닫아버리니까 데이터 호출 불가함.
    }

}

// ListActivity 를 이용하여 ListView 화면에 보여주기
//public class MainActivity extends ListActivity {
//
//    SQLiteDatabase sd;
//    ListView listView;
//
//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        Toast.makeText(this, "position = " + position, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        sd = openOrCreateDatabase("memo.db", 0, null);      // 얘는 apk에 포함안됨. 설치할 때 다 불러와야됨.. -> res에 넣으면 가능..
//        // create table                                                                 //device file/data/data/com.example.sqliteex/databases/memo.db 생성
//        String schema = "create table note (_id integer primary key autoincrement, "    //id : 값 안넣어줘도 자동증가
//                + "title text not null, body text not null);" ;
//        try {
//            sd.execSQL(schema);
//        } catch (Exception ignore){
//            Log.d("######################", "테이블 생성 문제 발생!!!"); // d(debug), i(information), e(error) 기능은 같음.
//        }
//
//        // record insert
//        ContentValues values = new ContentValues();                 // (key, value) 값 저장
//        values.put("title", "First");
//        values.put("body", "Hello");
//        sd.insert("note", null, values);
//
//
//        // ListView를 사용하여 SQLiteDatabase애서 읽어온 데이터 보여주기
//        //(1) ListView 객체화
////        listView = new ListView(this);
//        //(2) ListView에 공급되는 데이터
//        Cursor c = sd.query("note", null, null, null,
//                null, null, null);    // select * from note; 와 동일 기능
//        //(3) (ListView - 데이터) 연동을 위한 Adapter           // (데이터타입)Adapter --> ListAdapter, CursorAdapter
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,                 // Ctrl + 함수 클릭
//                R.layout.line, //한줄의 모양
//                c,  //공급되는 데이터
//                new String[]{"_id", "title", "body"},   //from
//                new int[]{R.id.index, R.id.title, R.id.body }   //to    - R.class 안에 int 로 정의됨.
//        );
//        //(4) ListView에 Adapter 적용
////        listView.setAdapter(adapter);
//
//        setListAdapter(adapter);    // ListActivity가 갖고 있는 ListView 보여줌. - 잘 안씀(안예쁨) but, List 클릭할 수 있는 callback 메소드 제공함
//
//
////        setContentView( R.layout.mainactivity );
//
//    }
//
//}
