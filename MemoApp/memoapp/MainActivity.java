package com.example.memoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

// Activity 상속받는 환경에서 ListView 화면에 보여주기
public class MainActivity extends Activity {

    // intent flag
    static final String MEMO_WRITE = "1";
    static final String MEMO_READ = "0";

    View view;  // 롱클릭 후 특정 메모 한라인을 삭제할경우 해당 라인에 애니메이션 적용을 위해 사용
    SimpleCursorAdapter adapter;
    String _id; //롱클릭된 메모의 "_id" 저장 -> 메모 삭제 용도로 사용

    SQLiteDatabase sd;
    ListView listView;
    Cursor c;
    Button write;


//    @Override
//    protected void onStop() {       // UI 이외의 자원 해제
//        super.onStop();
//        c.close();
//    }

    class MyDialogListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {   // Positive or Negative 구분해줌.
            if ( i == DialogInterface.BUTTON_POSITIVE ) {
                // DB에서 해당 메모 삭제
                String query = "delete from note where _id=" + _id;
                sd.execSQL(query);

                // 애니메이션 효과
                TranslateAnimation t = new TranslateAnimation(0, 800, 0, 0);
                t.setDuration(1500);    // 1초
                //t.setFillAfter(true);   // 움직인 후 다시 안돌아오도록.. 버그
                t.setAnimationListener(new Animation.AnimationListener() {  // 언제 끝날지 알아내기 위해 감시
                    @Override
                    public void onAnimationStart(Animation animation) {   }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // DB에서 메모 내용 다시 읽어오기
                        c = sd.query("note", null, null, null, null, null, null);
                        adapter.changeCursor(c);    // 바뀐 데이터 (cursor) 반영
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {   }
                });
                view.startAnimation(t);

            } else if ( i == DialogInterface.BUTTON_NEGATIVE ) {
                Toast.makeText(MainActivity.this, "취소하셨습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // DB에서 메모 내용 다시 읽어오기
        c = sd.query("note", null, null, null, null, null, null);
        adapter.changeCursor(c);    // 바뀐 데이터 (cursor) 반영
    }

    @Override
    protected void onStop() {
        super.onStop();
        c.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {        // UI 자원만 할당
        super.onCreate(savedInstanceState);
        setContentView( R.layout.mainactivity );    // findViewById 보다 이전에 와야함. UI 자원 먼저 가져오는 것임.

        // 쓰기 버튼
        write = findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SubActivity.class);
                i.putExtra("ACTION", MEMO_WRITE);
                startActivity(i);
            }
        });

        // SQLite
        sd = openOrCreateDatabase("memo.db", 0, null);      // 얘는 apk에 포함안됨. 설치할 때 다 불러와야됨.. -> res에 넣으면 가능..
        // create table                                                                 //device file/data/data/com.example.sqliteex/databases/memo.db 생성
        String schema = "create table note (_id integer primary key autoincrement, "    //id : 값 안넣어줘도 자동증가
                + "title text not null, body text not null);" ;
        try {
            sd.execSQL(schema);
        } catch (Exception ignore){
            Log.d("######################", "테이블 생성 문제 발생!!!"); // d(debug), i(information), e(error) 기능은 같음.
        }

//        // record insert
//        ContentValues values = new ContentValues();                 // (key, value) 값 저장
//        values.put("title", "First");
//        values.put("body", "Hello");
//        sd.insert("note", null, values);


        // ListView를 사용하여 SQLiteDatabase애서 읽어온 데이터 보여주기
        //(1) ListView 객체화
        listView = findViewById(R.id.list);
        //(2) ListView에 공급되는 데이터
        c = sd.query("note", null, null, null,              // UI 이외의 자원 할당 --> onStart()
                null, null, null);    // select * from note; 와 동일 기능
        //(3) (ListView - 데이터) 연동을 위한 Adapter           // (데이터타입)Adapter --> ListAdapter, CursorAdapter
        adapter = new SimpleCursorAdapter(this,                 // Ctrl + 함수 클릭
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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {     // view 에 클릭한 한 줄 넣어줌. (line.xml)
                LinearLayout layout = (LinearLayout) view;  // typecasting
                TextView t = (TextView) layout.getChildAt(0);   // textview(0번째)
                String _id = t.getText().toString();   // _id 값 가져오기
                //Toast.makeText(MainActivity.this, "_id = " + _id, Toast.LENGTH_SHORT).show();

                String title = "";
                String body = "";
                c.moveToFirst();
                while (c.isAfterLast() == false ) {
                    String t_id = c.getString(0);
                    // 클릭된 줄의 "_id"와 cursor에서 읽어온 _id가 같은지 확인
                    if ( _id.equals(t_id) ) {
                        title = c.getString(1);     // 이렇게 안해도 위에서 읽어오긴 함. 확인차
                        body = c.getString(2);
                        break;
                    }
                    c.moveToNext();
                }

                // 선택된 메모 세부 내용을 보여주기 위한 SubActivity 호출
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                intent.putExtra("ACTION", MEMO_READ);   // 클릭한 줄 번호(i)로만 확인불가(삭제때문) --> _id 가져와야함.
                intent.putExtra("TITLE", title);
                intent.putExtra("BODY", body);
                startActivity(intent);

                //Toast.makeText(MainActivity.this, "position = " + i, Toast.LENGTH_SHORT).show();
            }
        });

        // ListView 한 줄 롱클릭에 대한 처리
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 롱클릭된 한 줄을 저장, 애니메이션 효과를 적용하기 위해
                MainActivity.this.view = view;    // view = view 하면 안됨.

                // 롱클릭된 줄의 "_id" 가져오기
                TextView t = (TextView) ((LinearLayout)view).getChildAt(0);
                _id = t.getText().toString();

                // 팝업창
                MyDialogListener m = new MyDialogListener();
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("지우시겠습니까?")
                        .setPositiveButton("YES", m) //listener : 눌렀을 떄의 버튼 처리
                        .setNegativeButton("NO", m)
                        .show();

                //Toast.makeText(MainActivity.this, "LongClick", Toast.LENGTH_SHORT).show();
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
