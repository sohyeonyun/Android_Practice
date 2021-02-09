package com.example.locationex;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

// 현재 위치 정보 가져오기
// permission 획득 필요
// 안드로이드 6.0 이후 버전부터 위치정보 권한 획득 정책이 강화
// 프로그램 최초 실행시 다시 한 번 권한 획득 팝업을 띄워 줘야 함.

public class MainActivity extends Activity {

    TextView tv;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = new TextView(this);
        setContentView(tv);

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            //권한 요청 팝업창 --> 일반적으로 intro
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }
//        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                tv.setText( "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude() );

                // Google Map 이용해 현재 위치 보여줌.
                String pos = String.format("geo:%f,%f?z=16", location.getLatitude(), location.getLongitude());
                Uri uri = Uri.parse(pos);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);

            }
        });  //네트워크 기준 위치 제공, 몇 초마다 값가져올지, 얼마 거리 기준 값 가져올지,

    }
}