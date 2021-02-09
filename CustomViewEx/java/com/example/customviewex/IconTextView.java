package com.example.customviewex;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

// ListView에 보여줄 데이터가 셋팅된 한 줄 그 자체  --> getView 간단히 해주려고
public class IconTextView extends LinearLayout {
    // line.xml에 정의된 한 줄의 구성요소를 멤버변수로 선언
    LinearLayout layout;
    TextView t1, t2, t3;

    public IconTextView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   //setContentView 못쓰니까
        inflater.inflate(R.layout.line, this, true);  //layout : line.xml을 어디에 귀속시킬지    //지금은 LinearLayout 상속받고 있기 때문에 this

        t1 = this.findViewById(R.id.textView);   //root 안에서 findViewById 해야함.
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        layout = findViewById(R.id.image);
    }
}
