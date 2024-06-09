package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        db = FirebaseFirestore.getInstance();

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("오늘의 데이트");

        // 뒤로가기 버튼 설정
        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료, 이전 화면으로 돌아감
            }
        });

        Button btnSaveResult = findViewById(R.id.btn_save_result);
        EditText editTextName = findViewById(R.id.edit_text_name);

        btnSaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                if (!name.isEmpty()) {
                    saveResult(name);
                }
                Intent intent = new Intent(ResultActivity.this, NaviActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finishAffinity();
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

        HashMap<String, Object> recommendedRestaurant = (HashMap<String, Object>) intent.getSerializableExtra("recommendedRestaurant");
        HashMap<String, Object> recommendedCafe = (HashMap<String, Object>) intent.getSerializableExtra("recommendedCafe");
        HashMap<String, Object> recommendedActivity = (HashMap<String, Object>) intent.getSerializableExtra("recommendedActivity");

        if (recommendedRestaurant != null) {
            Map<String, Object> restaurantItem = new HashMap<>();
            restaurantItem.put("name", recommendedRestaurant.get("name"));
            restaurantItem.put("tags", recommendedRestaurant.get("tags").toString());
            restaurantItem.put("latitude", recommendedRestaurant.get("latitude"));
            restaurantItem.put("longitude", recommendedRestaurant.get("longitude"));
            dateItems.add(restaurantItem);
        } else {
            Map<String, Object> restaurantItem = new HashMap<>();
            restaurantItem.put("name", "Recommended Restaurant: No recommendation found");
            restaurantItem.put("tags", "");
            restaurantItem.put("latitude", "");
            restaurantItem.put("longitude", "");
            dateItems.add(restaurantItem);
        }

        if (recommendedCafe != null) {
            Map<String, Object> cafeItem = new HashMap<>();
            cafeItem.put("name", recommendedCafe.get("name"));
            cafeItem.put("tags", recommendedCafe.get("tags").toString());
            cafeItem.put("latitude", recommendedCafe.get("latitude"));
            cafeItem.put("longitude", recommendedCafe.get("longitude"));
            dateItems.add(cafeItem);
        } else {
            Map<String, Object> cafeItem = new HashMap<>();
            cafeItem.put("name", "Recommended Cafe: No recommendation found");
            cafeItem.put("tags", "");
            cafeItem.put("latitude", "");
            cafeItem.put("longitude", "");
            dateItems.add(cafeItem);
        }

        if (recommendedActivity != null) {
            Map<String, Object> activityItem = new HashMap<>();
            activityItem.put("name", recommendedActivity.get("name"));
            activityItem.put("tags", recommendedActivity.get("tags").toString());
            activityItem.put("latitude", recommendedActivity.get("latitude"));
            activityItem.put("longitude", recommendedActivity.get("longitude"));
            dateItems.add(activityItem);
        } else {
            Map<String, Object> activityItem = new HashMap<>();
            activityItem.put("name", "Recommended Activity: No recommendation found");
            activityItem.put("tags", "");
            activityItem.put("latitude", "");
            activityItem.put("longitude", "");
            dateItems.add(activityItem);
        }

        return dateItems;
    }

    private void saveResult(String name) {
        Intent intent = getIntent();

        HashMap<String, Object> recommendedRestaurant = (HashMap<String, Object>) intent.getSerializableExtra("recommendedRestaurant");
        HashMap<String, Object> recommendedCafe = (HashMap<String, Object>) intent.getSerializableExtra("recommendedCafe");
        HashMap<String, Object> recommendedActivity = (HashMap<String, Object>) intent.getSerializableExtra("recommendedActivity");

        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("recommendedRestaurant", recommendedRestaurant);
        result.put("recommendedCafe", recommendedCafe);
        result.put("recommendedActivity", recommendedActivity);

        db.collection("savedResults")
                .add(result)
                .addOnSuccessListener(documentReference -> {
                    // 저장 성공 시 처리할 작업
                })
                .addOnFailureListener(e -> {
                    // 저장 실패 시 처리할 작업
                });
    }
}
