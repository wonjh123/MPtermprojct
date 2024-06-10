package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("오늘의 데이트");

        // 뒤로가기 버튼 설정
        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(v -> finish()); // 현재 액티비티 종료, 이전 화면으로 돌아감

        Button btnSaveResult = findViewById(R.id.btn_save_result);
        EditText editTextName = findViewById(R.id.edit_text_name);

        btnSaveResult.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            if (!name.isEmpty()) {
                saveResult(name);
            } else {
                Toast.makeText(ResultActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.date_item_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Map<String, Object>> dateItems = getIntentData();
        DateItemAdapter adapter = new DateItemAdapter(dateItems);
        recyclerView.setAdapter(adapter);
    }

    private List<Map<String, Object>> getIntentData() {
        List<Map<String, Object>> dateItems = new ArrayList<>();

        Intent intent = getIntent();

        String recommendedRestaurantName = intent.getStringExtra("recommendedRestaurantName");
        ArrayList<String> recommendedRestaurantTags = intent.getStringArrayListExtra("recommendedRestaurantTags");
        double recommendedRestaurantLatitude = intent.getDoubleExtra("recommendedRestaurantLatitude", 0);
        double recommendedRestaurantLongitude = intent.getDoubleExtra("recommendedRestaurantLongitude", 0);

        if (recommendedRestaurantName != null) {
            Map<String, Object> restaurantItem = new HashMap<>();
            restaurantItem.put("name", recommendedRestaurantName);
            restaurantItem.put("tags", recommendedRestaurantTags);
            restaurantItem.put("latitude", recommendedRestaurantLatitude);
            restaurantItem.put("longitude", recommendedRestaurantLongitude);
            dateItems.add(restaurantItem);
        }

        String recommendedCafeName = intent.getStringExtra("recommendedCafeName");
        ArrayList<String> recommendedCafeTags = intent.getStringArrayListExtra("recommendedCafeTags");
        double recommendedCafeLatitude = intent.getDoubleExtra("recommendedCafeLatitude", 0);
        double recommendedCafeLongitude = intent.getDoubleExtra("recommendedCafeLongitude", 0);

        if (recommendedCafeName != null) {
            Map<String, Object> cafeItem = new HashMap<>();
            cafeItem.put("name", recommendedCafeName);
            cafeItem.put("tags", recommendedCafeTags);
            cafeItem.put("latitude", recommendedCafeLatitude);
            cafeItem.put("longitude", recommendedCafeLongitude);
            dateItems.add(cafeItem);
        }

        String recommendedActivityName = intent.getStringExtra("recommendedActivityName");
        ArrayList<String> recommendedActivityTags = intent.getStringArrayListExtra("recommendedActivityTags");
        double recommendedActivityLatitude = intent.getDoubleExtra("recommendedActivityLatitude", 0);
        double recommendedActivityLongitude = intent.getDoubleExtra("recommendedActivityLongitude", 0);

        if (recommendedActivityName != null) {
            Map<String, Object> activityItem = new HashMap<>();
            activityItem.put("name", recommendedActivityName);
            activityItem.put("tags", recommendedActivityTags);
            activityItem.put("latitude", recommendedActivityLatitude);
            activityItem.put("longitude", recommendedActivityLongitude);
            dateItems.add(activityItem);
        }

        return dateItems;
    }

    private void saveResult(String name) {
        Intent intent = getIntent();

        String recommendedRestaurantName = intent.getStringExtra("recommendedRestaurantName");
        ArrayList<String> recommendedRestaurantTags = intent.getStringArrayListExtra("recommendedRestaurantTags");
        double recommendedRestaurantLatitude = intent.getDoubleExtra("recommendedRestaurantLatitude", 0);
        double recommendedRestaurantLongitude = intent.getDoubleExtra("recommendedRestaurantLongitude", 0);

        String recommendedCafeName = intent.getStringExtra("recommendedCafeName");
        ArrayList<String> recommendedCafeTags = intent.getStringArrayListExtra("recommendedCafeTags");
        double recommendedCafeLatitude = intent.getDoubleExtra("recommendedCafeLatitude", 0);
        double recommendedCafeLongitude = intent.getDoubleExtra("recommendedCafeLongitude", 0);

        String recommendedActivityName = intent.getStringExtra("recommendedActivityName");
        ArrayList<String> recommendedActivityTags = intent.getStringArrayListExtra("recommendedActivityTags");
        double recommendedActivityLatitude = intent.getDoubleExtra("recommendedActivityLatitude", 0);
        double recommendedActivityLongitude = intent.getDoubleExtra("recommendedActivityLongitude", 0);

        Map<String, Object> result = new HashMap<>();
        result.put("name", name);

        if (recommendedRestaurantName != null) {
            Map<String, Object> recommendedRestaurant = new HashMap<>();
            recommendedRestaurant.put("name", recommendedRestaurantName);
            recommendedRestaurant.put("tags", recommendedRestaurantTags);
            recommendedRestaurant.put("location", new GeoPoint(recommendedRestaurantLatitude, recommendedRestaurantLongitude));
            result.put("recommendedRestaurant", recommendedRestaurant);
        }

        if (recommendedCafeName != null) {
            Map<String, Object> recommendedCafe = new HashMap<>();
            recommendedCafe.put("name", recommendedCafeName);
            recommendedCafe.put("tags", recommendedCafeTags);
            recommendedCafe.put("location", new GeoPoint(recommendedCafeLatitude, recommendedCafeLongitude));
            result.put("recommendedCafe", recommendedCafe);
        }

        if (recommendedActivityName != null) {
            Map<String, Object> recommendedActivity = new HashMap<>();
            recommendedActivity.put("name", recommendedActivityName);
            recommendedActivity.put("tags", recommendedActivityTags);
            recommendedActivity.put("location", new GeoPoint(recommendedActivityLatitude, recommendedActivityLongitude));
            result.put("recommendedActivity", recommendedActivity);
        }

        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid())
                    .collection("LastDates")
                    .add(result)
                    .addOnSuccessListener(documentReference -> {
                        // 저장 성공 시 처리할 작업
                        Toast.makeText(ResultActivity.this, "결과가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // 저장 실패 시 처리할 작업
                        Toast.makeText(ResultActivity.this, "저장 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(ResultActivity.this, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
