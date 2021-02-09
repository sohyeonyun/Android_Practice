package com.example.busapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ArrInfoAdapter extends BaseAdapter {
    ArrayList<ArrInfo> data;
    Context context;

    public ArrInfoAdapter(Context con, ArrayList<ArrInfo> total) {    // 생성자.. context 받아옴.
        context = con;
        data = total;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {    // ListView가 줄마다 getView() 호출
        //LayoutInflater : activity에서 setContentView()의 역할 (사용 위해 메모리에 올려줌.)
        LinearLayout layout = new LinearLayout(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.arrinfo, layout, true);   // arrinfo를 layout으로 종속시킴. layout안에서 id 찾아오면 됨.

        ArrInfo temp = data.get(i); //i 번째 줄에 보여줄 도착 정보 꺼내오기.
        TextView route_no = layout.findViewById(R.id.route_no);
        route_no.setText(temp.route_no);
        TextView arr_state = layout.findViewById(R.id.arr_state);
        arr_state.setText(temp.arr_state);
        TextView cur_pos = layout.findViewById(R.id.cur_pos);
        cur_pos.setText(temp.cur_pos);

        return layout;
    }

    @Override
    public int getCount() {
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
