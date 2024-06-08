
        package kr.ac.gachon.recommendate;

        import android.Manifest;
        import android.annotation.SuppressLint;
        import android.app.FragmentManager;
        import android.content.pm.PackageManager;
        import android.location.Location;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapFragment;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.gun0912.tedpermission.PermissionListener;
        import com.gun0912.tedpermission.normal.TedPermission;

        import java.util.List;


        public class LocationMap extends AppCompatActivity implements OnMapReadyCallback {

            private FragmentManager fragmentManager;
            private MapFragment mapFragment;
            private Thread thread;
            private LocationManager locationManager;
            Location location;
            double lattitude, longitude;

            private Boolean isFine = false;
            private RelativeLayout rootLayout;
            private LinearLayout linearLayout;
            private Button myLocationBtn, applyBtn;
            private EditText et_name;
            private boolean isapply = true;
            private LatLng latLngForMarker;

            String[] mark_title, mark_lati,mark_long;

            GoogleMap map;
            private boolean onMarker = true;
            Marker marker, marker_retouch;

            private final static String TAG = "LocationMap.java";
            private final static int UPDATELOCATION = 0;
            private int mpHeight, lastY, curY;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.mygoogle_map);

                TedPermission
                        .create()
                        .setPermissionListener(permission)
                        .setRationaleMessage("위치확인을 위한 권한 필요")
                        .setDeniedMessage("권한이 거부되었습니다. 설정->권한에서 허용할 수 있습니다")
                        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                        .check();

                fragmentManager = getFragmentManager();
                mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.my_google_map);
                mapFragment.getMapAsync(this);

                rootLayout = (RelativeLayout) findViewById(R.id.root_relative_layout);

                /*
                linearLayout = (LinearLayout) findViewById(R.id.marker_layout);
                et_name = (EditText) findViewById(R.id.edit_mark_name);
                applyBtn = (Button) findViewById(R.id.apply_mark_btn);

                applyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isapply) {
                            if (et_name.getText().toString() == "") {
                                Toast.makeText(getApplicationContext(), "마커 이름을 입력하십시오",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.title(et_name.getText().toString());
                                markerOptions.position(latLngForMarker);
                                map.addMarker(markerOptions);
                                isapply = false;
                                applyBtn.setText("수정");
                            }

                        } else {
                            marker_retouch.setTitle(et_name.getText().toString());
                        }
                    }
                });

                 */

                myLocationBtn = (Button) findViewById(R.id.btn_toMyLocation);
                myLocationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onMarker = true;
                        myLocationBtn.setVisibility(View.INVISIBLE);
                        Log.d(TAG, ("onCLickListener : onMarker = " + onMarker));
                        LatLng location = new LatLng(lattitude, longitude);
                        map.moveCamera(CameraUpdateFactory.newLatLng(location));
                    }
                });


                // 지속적으로 위치 받아오기
                thread = new Thread() {
                    @SuppressLint("MissionPermission")
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (isFine) {
                                if (ActivityCompat.checkSelfPermission(LocationMap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                        && ActivityCompat.checkSelfPermission(LocationMap.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                lattitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                            handler.sendEmptyMessage(UPDATELOCATION);
                            Log.d(TAG, "run : threadIsRunning");
                        }
                    }
                };
                thread.start();

                /*
                linearLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                                linearLayout.getLayoutParams();
                        int action = event.getAction();
                        if(action == MotionEvent.ACTION_DOWN){
                            lastY = (int) event.getY() + layoutParams.topMargin;
                        } else if(action == MotionEvent.ACTION_MOVE && Math.abs(lastY - layoutParams.topMargin)
                                < 100 && lastY < (mpHeight - 200) && lastY > 100){
                            curY = (int)event.getY() + layoutParams.topMargin;
                            layoutParams.setMargins(0, (layoutParams.topMargin + curY-lastY), 0, 0);
                            linearLayout.setLayoutParams(layoutParams);
                            lastY = curY;
                        }
                        return true;
                    }
                });

                 */

            }

            @Override
            public void onMapReady(@NonNull @org.jetbrains.annotations.NotNull
                                   GoogleMap googleMap){
                map = googleMap;
                // 1. 첫 지도 위치를 서울로 초기화해두기
                LatLng initialLocation = new LatLng(37.52487,126.92723);

                // 2. 지도에 marker 만들기 위한 좌표
                //LatLng location = new LatLng(0,0);
                // 2-1. 마커 테스트를 위해 현 위치를 '서울'로 하여 진행합니다.
                LatLng location = new LatLng(37.52487,126.92723);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title("현위치");
                markerOptions.draggable(true);
                markerOptions.position(location);

                marker = googleMap.addMarker(markerOptions);
                // 현위치 마커 위치로 이동하기

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));

                // 테스트를 위한 서울 주변 카페, 음식점 마커 생성
                mark_title = getResources().getStringArray(R.array.식당_카페);
                mark_lati = getResources().getStringArray(R.array.위도);
                mark_long = getResources().getStringArray(R.array.경도);
                for(int i = 0; i<mark_title.length; i++){
                    MarkerOptions markerOptions1 = new MarkerOptions();
                    markerOptions1
                            .position(new LatLng(Double.parseDouble(mark_lati[i]), Double.parseDouble(mark_long[i])))
                            .title(mark_title[i]);

                    map.addMarker(markerOptions1);
                }


                // 지도 이동 시
                googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        Log.d(TAG, "onCameraMove : TouchListenerIsRunning");

                        if(Math.abs(map.getCameraPosition().target.latitude - lattitude)
                                < 0.0005 && Math.abs(map.getCameraPosition().target.longitude - longitude) < 0.0005){
                            onMarker = true;
                            myLocationBtn.setVisibility(View.INVISIBLE);
                            Log.d(TAG, ("onCameraMove : onMarker = " + onMarker));
                        }else {
                            onMarker = false;
                            myLocationBtn.setVisibility(View.VISIBLE);
                            Log.d(TAG, ("onCameraMove : onMarker = " + onMarker));
                        }

                    }
                });

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker){
                        if(marker.getTitle().toString() != "현위치"){
                            marker_retouch = marker;
                            et_name.setText(marker.getTitle());
                        }else {
                            onMarker = true;
                            myLocationBtn.setVisibility(View.INVISIBLE);
                        }
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(marker.getPosition().latitude,
                                marker.getPosition().longitude)));
                        return true;
                    }
                });

                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull LatLng latLng) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                                linearLayout.getLayoutParams();
                        isapply = true;
                        applyBtn.setText("생성");
                        layoutParams.setMargins(0, mpHeight/2+100, 0, 0);
                        linearLayout.setLayoutParams(layoutParams);
                        myLocationBtn.setVisibility(View.VISIBLE);
                        onMarker = false;
                        latLngForMarker = latLng;
                    }
                });

            }

            // 권한 요청 시
            PermissionListener permission = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    Toast.makeText(LocationMap.this, "권한 허가", Toast.LENGTH_SHORT);
                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    isFine = true;
                }
                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    Toast.makeText(LocationMap.this, "권한 거부", Toast.LENGTH_SHORT);
                }
            };

            private Handler handler = new Handler(){

                public void handelMessage(@NonNull Message msg){
                    if(msg.what == UPDATELOCATION){
                        LatLng location = new LatLng(lattitude, longitude);
                        marker.setPosition(location);
                        Log.d(TAG, ("handleMessage: onMarker = " + onMarker));
                        if(onMarker){
                            map.moveCamera(CameraUpdateFactory.newLatLng(location));
                        }
                    }
                }
            };

            @Override
            public void onWindowFocusChanged(boolean hasFocus){
                super.onWindowFocusChanged(hasFocus);
                mpHeight = rootLayout.getHeight();
            }



            /*
            @Override
            public void onMapReady(final GoogleMap googleMap){
                myMap = googleMap;
                LatLng seoul = new LatLng(37.56, 126.97);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(seoul);
                markerOptions.title("서울");
                markerOptions.snippet("수도");
                myMap.addMarker(markerOptions);

                myMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
                myMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            }

             */

        }


