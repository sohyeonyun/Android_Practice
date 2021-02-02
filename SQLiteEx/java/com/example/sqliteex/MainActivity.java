package com.example.sqliteex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
    SQLiteDatabase sd;  // 안드로이드 내의 SQLite 사용하기 위한 클래스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sd = openOrCreateDatabase("test.db", 0, null);
        // create table                                                                 //device file/data/data/com.example.sqliteex/databases/test.db 생성
        String schema = "create table note (_id integer primary key autoincrement, "    //id : 값 안넣어줘도 자동증가
                        + "title text not null, body text not null);" ;
        try {
            sd.execSQL(schema);
        } catch (Exception ignore){
            Log.d("######################", "테이블 생성 문제 발생!!!"); // d(debug), i(information), e(error) 기능은 같음.
        }

        // record insert
        ContentValues values = new ContentValues();                 // (key, value) 값 저장
        values.put("title", "Second");
        values.put("body", "안뇽");
        sd.insert("note", null, values);


        // select    - 실제론 이 방식 안씀. 작동 방식 이해위해.        // 파일 읽기 - viewer or console(!)
        Cursor c = sd.query("note", null, null, null, null, null, null);    // select * from note; 와 동일 기능
        String temp = "";
        for (int i = 0 ; i < c.getColumnCount() ; i++ ) {   // col 이름 가져옴(_id, title, body)
            temp += c.getColumnName(i) + '\t' + " || ";
        }
        temp += '\n';   // 줄 내림

        c.moveToFirst();    // 다음 줄(저장된 데이터의 첫 번째 줄)로 커서를 보냄.
        while (c.isAfterLast() == false) {  // 마지막 줄까지 데이터 읽어옴.
            for (int i = 0 ; i < c.getColumnCount() ; i++) {
                temp += c.getString(i) + '\t' + "||";
            }
            temp += '\n';
            c.moveToNext();
        }
        c.close();  // Cursor는 자원 해제 꼭 필요! -> 원래 onStrop()에 드감.

        TextView t = new TextView(this);
        t.setText(temp);
        setContentView(t);

    }
}