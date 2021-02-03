package com.example.memoapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SubActivity extends Activity {

    EditText title, body;
    Button button;
    SQLiteDatabase sd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {  // @ : 지능형 주석 - @Override = 오버라이딩된 메소드 검증해줌.
        super.onCreate(savedInstanceState);

        // SQLite
        sd = openOrCreateDatabase("memo.db", 0, null);

        Intent i = getIntent();
        String action = i.getStringExtra("ACTION");
        setContentView(R.layout.subactivity);
        title = findViewById(R.id.title2);
        body = findViewById(R.id.body2);
        button = findViewById(R.id.save);

        switch(action) {
            case MainActivity.MEMO_READ:
                title.setEnabled(false);
                body.setEnabled(false);
                button.setText("BACK");
                title.setText( i.getStringExtra("TITLE") );  // 값 가져와서 설정
                body.setText( i.getStringExtra("BODY") );
                break;
            case MainActivity.MEMO_WRITE:
                break;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( action.equals(MainActivity.MEMO_WRITE) ) {
                    // save 버튼 클릭했을 경우 title, body에 입력된 내용 DB에 저장
                    ContentValues values = new ContentValues();                 // (key, value) 값 저장
                    values.put("title", title.getText().toString());           // getText() : String 클래스 X
                    values.put("body", body.getText().toString());
                    sd.insert("note", null, values);
                    Toast.makeText(SubActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
                finish();   // 현재 화면 종료
            }
        });


    }



}
