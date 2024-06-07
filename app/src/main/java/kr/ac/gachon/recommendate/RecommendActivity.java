package kr.ac.gachon.recommendate;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RecommendActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FragmentManager fragmentManager;
    // private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("오늘 어디서 만나고 싶으신가요?");

        // 구글 맵
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        // 뒤로가기 버튼 설정
        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료, 이전 화면으로 돌아감
            }
        });


        /*
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.my_google_map);
        mapFragment.getMapAsync(this);

         */
        
        // 설정하기 버튼
        Button btnChooseLocation = findViewById(R.id.btn_choose_location);
        btnChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TagActivity로 이동하는 Intent 생성
                Intent intent = new Intent(RecommendActivity.this, TagActivity.class);
                startActivity(intent); // TagActivity 시작
            }
        });
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.556, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.draggable(true);
        markerOptions.snippet("한국 수도");

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 16));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }
}