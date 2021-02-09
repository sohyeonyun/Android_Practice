package com.example.busapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

// 버스 정류장 정보를 보여주기 위한 화면
public class StationInfoActivity extends Activity {

    Button search;  // 정류장 검색 버튼
    ListView listView;
    StationInfoAdapter adapter;

    // 정류장 정보를 저장하고 있는 StationInfo 객체를 저장
    ArrayList<StationInfo> total = new ArrayList<StationInfo>();

    String result;
    EditText et;

    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
//            et.setText(result);
            //(3) ListView 와 데이터를 중계할 Adapter 생성
            adapter = new StationInfoAdapter(StationInfoActivity.this, total);
            //(4) ListView 에 Adapter 설정
            listView.setAdapter(adapter);
        }
    }
    MyHandler myHandler = new MyHandler();  // myHandler 는 쓰레드 안에서 객체화 불가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stationinfoactivity);   //만든 activity의 listview 보여줌
//        et = new EditText(this);
//        setContentView(et); //만든 후 보여준다.

        //EditText(지명 입력을 위한 입력창) 가져오기
        et = findViewById(R.id.edittext);

       //검색 버튼 찾아오기
       search = findViewById(R.id.search);
       //검색 버튼 클릭 이벤트 처리
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //키보드 비활성화
                InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(et.getWindowToken(), 0); //키보드 주체 넘겨줌.

                //이전 검색 정보 초기화
                total.clear();

                //(2) ListView 에 공급되는 데이터 설정
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://businfo.daegu.go.kr/ba/arrbus/arrbus.do");
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setDoOutput(true);
                            String parameter = "act=findByBusStopNo&bsNm=" + et.getText();
                            OutputStream wr = con.getOutputStream();
                            wr.write(parameter.getBytes("euc-kr"));     // "euc-kr" 타입으로 바이트 얻어옴.
                            wr.flush();
                            BufferedReader rd = new BufferedReader( new InputStreamReader(con.getInputStream(), "euc-kr") );    // Byte 타입 -> 문자 타입

                            // HTML Parsing - jericho(libs에 추가)
                            Source src = new Source(rd);
                            src.fullSequentialParse();  // 모든 엘리먼트(태그)를 객체화
                            List<Element> all = src.getAllElements();  // 모든 엘리먼트 리스트로 가져오기

                            StationInfo data = new StationInfo();
                            int count = 0;
                            for (Element element : all) {   // Element element = all.get(i)
                                String name = element.getName();    //태그 이름 가져옴.
                                if (name.equals("td")) {    // <tr> 가져옴.
                                    switch (count) {
                                        case 0: //정류장명
                                            data = new StationInfo();
                                            data.station_name = element.getTextExtractor().toString();
                                            count++;
                                            break;
                                        case 1: //정류장번호
                                            data.station_number = element.getTextExtractor().toString();
                                            total.add(data);    // 정류장 정보 저장
                                            count = 0;
                                            break;
                                    }
//                            result += element.getTextExtractor();
//                            result += '\n';
                                }
                            }

                            //쓰레드 안에선 화면 갱신 X --> Handler 필요
                            myHandler.sendEmptyMessage(0);  //Handler 호출 타이밍

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });


        //(1) ListView 생성(객체화)
//        listView = new ListView(this);
        listView = findViewById(R.id.listview);

        //ListView 한 줄 클릭이벤트 처리 --> 해당 정류장 도착정보를 확인하기 위해 MainActivity 호출
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { // view : 선택된 한 줄
                //클릭된 한줄에서 정류장 번호 가져오기
                LinearLayout layout = (LinearLayout) view;  // view는 getView()시 넘어오는 값. (LinearLayout 타입)
                TextView t = (TextView) layout.getChildAt(1);   // 정류장 이름 가져오기

                Intent intent = new Intent(StationInfoActivity.this, MainActivity.class);
                intent.putExtra("number", t.getText().toString());
                startActivity(intent);
            }
        });

        //원래 여기 (2) 번


    }
}
