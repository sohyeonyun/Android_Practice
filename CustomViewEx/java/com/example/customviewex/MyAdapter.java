package com.example.customviewex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {    // BaseAdapter : 추상클래스
    Context context;
    ArrayList<IconTextItem> data;

    public MyAdapter(Context c, ArrayList<IconTextItem> data) {   // 생성자(new될때 한 번만 호출됨, return없고, 클래스 이름과 같음)..!
        context = c;        //context 받아옴.
        this.data = data;   // 데이터 받아옴.
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {    // 한 줄 한 줄 보여줌. getCount()만큼 getView() 호출됨.

        // ListView에 보여줄 한 줄 자체를 정의하고 있는 클래스를 활용하는 경우
        IconTextView line = new IconTextView(context);
        IconTextItem iconTextItem = data.get(i);
        line.layout.setBackgroundResource(iconTextItem.res_id);
        line.t1.setText(iconTextItem.t1);
        line.t2.setText(iconTextItem.t2);
        line.t2.setText(iconTextItem.t3);

        return line;

        // IconTextView 사용하지 않고 getView()에서 직접 화면과 데이터를 구성하는 예제
//        LinearLayout layout = new LinearLayout(context);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);    //setContentView 못쓰니까
//        inflater.inflate(R.layout.line, layout, true);  //layout : line.xml을 어디에 귀속시킬지
//
//        LinearLayout image = layout.findViewById(R.id.image);
//        TextView t1 = layout.findViewById(R.id.textView);   //root 안에서 findViewById 해야함.
//        TextView t2 = layout.findViewById(R.id.textView2);
//        TextView t3 = layout.findViewById(R.id.textView3);
//
//        IconTextItem iconTextItem = data.get(i);    // i 번째 줄에 보여줄 데이터 가져옴.
//        image.setBackgroundResource(iconTextItem.res_id);
//        t1.setText(iconTextItem.t1);
//        t2.setText(iconTextItem.t2);
//        t3.setText(iconTextItem.t3);
//
//        return layout;


        // 임의 데이터 예시
//        TextView t1 = new TextView(context);        // this는 현재 Activity 클래스를 뜻함. 그래서 여기선 못씀. -> MyAdapter(c) 에서 받아옴.
//        TextView t2 = new TextView(context);
//        t1.setText("ABC");
//        t2.setText("DEF");
//        LinearLayout layout = new LinearLayout(context);
//        layout.addView(t1);
//        layout.addView(t2);
//        return layout;
    }

    @Override
    public int getCount() { //ListView가 보여주게 될 줄의 개수를 리턴    // Adapter 호출시 가장 먼저 실행됨.
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
