package com.example.customviewex;

// ListView 한 줄에 공급하게 될 데이터 묶음
public class IconTextItem {
    int res_id;
    String t1, t2, t3;

    public IconTextItem(int res_id, String t1, String t2, String t3) {  // 생성자 Constructor - new 될 때만 한 번 실행
        this.res_id = res_id;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }
}
