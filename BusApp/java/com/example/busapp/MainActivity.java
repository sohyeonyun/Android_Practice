package com.example.busapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    String station_number;  // 이전 화면에서 넘겨준 정류장 번호를 저장하기 위한 공간
    ListView listView;  //버스 도착정보를 보여주기 위한 ListView
    ArrInfoAdapter adapter;
    // 버스 도착 정보를 저장하기 위한 ArrInfo 객체를 모두 관리위한 컬렉션 프레임위크크
    ArrayList<ArrInfo> arrInfo_all = new ArrayList<ArrInfo>();
    EditText tv;
    String result = "";

    // 화면 갱신용 Handler (데이터 받아온 보장 O)
    Handler handler = new Handler(){    // 익명 내부클래스 = 클래스 정의 + 선언 한번에
        @Override
        public void handleMessage(@NonNull Message msg) {   // {} 안에 Alt + Insert -> Override
//            tv.setText(result.trim());

            //(3) ListView 와 공급되는 데이터 연동을 위한 Adapter 생성
            adapter = new ArrInfoAdapter(MainActivity.this, arrInfo_all);   //객체라 데이터 없어도 괜찮. 가독성 위해 여기로.

            //(4) ListView 에 Adapter 설정 - 데이터 보장 필요!
            listView.setAdapter(adapter);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        tv = new EditText(this);
//        setContentView(tv);

        Intent i = getIntent();
        station_number = i.getStringExtra("number");

        //(1) ListView 생성(객체화)
        listView = new ListView(this);

        //(2) 공급되는 데이터
        new Thread(){   // 네트워크 사용시 쓰레드 필요
            public void run() {
                try {
                    URL url = new URL("http://businfo.daegu.go.kr/ba/arrbus/arrbus.do");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
//                    String parameter = "act=arrbus&winc_id=02104";
                    String parameter = "act=arrbus&winc_id=" + station_number;
                    OutputStream wr = con.getOutputStream();
                    wr.write(parameter.getBytes());
                    wr.flush();
                    BufferedReader rd = new BufferedReader( new InputStreamReader(con.getInputStream(), "euc-kr") );    // Byte 타입 -> 문자 타입

                    // HTML Parsing - jericho(libs에 추가)
                    Source src = new Source(rd);
                    src.fullSequentialParse();  // 모든 엘리먼트(태그)를 객체화
                    List<Element> all = src.getAllElements();  // 모든 엘리먼트 리스트로 가져오기

                    ArrInfo data = new ArrInfo();
                    for (int i = 0 ; i < all.size(); i++) {
                        Element element = all.get(i);
                        if ( element.getName().equals("span") ) {     // 엘리먼트 이름 하나씩 확인,, "span" 태그 기준 분리
                            //<span> 태그 내의 "class" 속성의 값 가져오기
                            String value = element.getAttributeValue("class");  // "class" 이름 가져옴.
                            switch (value) {
                                case "route_no":    // 새로운 버스
                                    data = new ArrInfo();
                                    data.route_no = element.getTextExtractor().toString();
                                    break;
                                case "arr_state":
                                    data.arr_state = element.getTextExtractor().toString();
                                    break;
                                case "cur_pos busNN":
                                case "cur_pos busDN":
                                    data.cur_pos = element.getTextExtractor().toString();
                                    arrInfo_all.add(data);
                                    break;
                            }
                            // equals("li") 일 때
//                            result += element.getTextExtractor();             // 현재 태그 하위의 모든 값 가져오기
//                            result += '\n';     // 한 줄 내림
                        }

                    }
                    // 데이터 파싱 끝

//                    String line = null;
//                    while ((line = rd.readLine()) != null) {    // BufferedReader 는 한 줄 단위로 읽기 가능
//                        result += line;
//                        result += '\n';
//                    }
                    handler.sendEmptyMessage(0);

                } catch ( Exception e ) {
                    Log.d("#########", "오류 발생");
                    Log.e("#########", e.toString());   // 빨간색 로그
                    e.printStackTrace();                    // 에러나는 위치 표시
                }
            }
        }.start();

        // 데이터 받아왔다는 보장 없기 떄문에 여기에 X --> Handler
//        //(3) ListView 와 공급되는 데이터 연동을 위한 Adapter 생성
//        adapter = new ArrInfoAdapter(this, arrInfo_all);
//        //(4) ListView 에 Adapter 설정
//        listView.setAdapter(adapter);

        setContentView(listView);   //!!
    }
}




/*
    android:usesCleartextTraffic="true" --> http 한시적으로 허용
 */

/*
    character-set
    문자 -> 2진수 변환 규칙
    아스키코드표

    한글처리, character-set
    utf-8 : 전세계 모든 언어 포함, 한글처리 위해 3byte
    euc-kr : 한글만 추가, 한글처리 위해 2byte
        둘 중 사이트에 맞는 변환기 맞춰주면 됨.
 */
