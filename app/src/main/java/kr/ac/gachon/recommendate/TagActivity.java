package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TagActivity extends AppCompatActivity {

    private List<String> selectedRestaurantTags = new ArrayList<>();
    private List<String> selectedCafeTags = new ArrayList<>();
    private List<String> selectedActivityTags = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        db = FirebaseFirestore.getInstance();

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("오늘의 분위기는 어떤가요?");

        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료, 이전 화면으로 돌아감
            }
        });

        Button btnGetResult = findViewById(R.id.btn_get_result);
        btnGetResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecommendation();
            }
        });

        List<String> restaurantTags = getRestaurantTags();
        RecyclerView restaurantRecyclerView = findViewById(R.id.recyclerView1);
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TagToggleAdapter restaurantAdapter = new TagToggleAdapter(restaurantTags, selectedRestaurantTags);
        restaurantRecyclerView.setAdapter(restaurantAdapter);

        List<String> cafeTags = getCafeTags();
        RecyclerView cafeRecyclerView = findViewById(R.id.recyclerView2);
        cafeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TagToggleAdapter cafeAdapter = new TagToggleAdapter(cafeTags, selectedCafeTags);
        cafeRecyclerView.setAdapter(cafeAdapter);

        List<String> activityTags = getActivityTags();
        RecyclerView activityRecyclerView = findViewById(R.id.recyclerView3);
        activityRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TagToggleAdapter activityAdapter = new TagToggleAdapter(activityTags, selectedActivityTags);
        activityRecyclerView.setAdapter(activityAdapter);
    }



    private List<String> getRestaurantTags() {
        List<String> restaurantTags = new ArrayList<>();
        restaurantTags.add("#한식");
        restaurantTags.add("#중식");
        restaurantTags.add("#일식");
        restaurantTags.add("#양식");
        // 필요에 따라 데이터를 추가합니다.
        return restaurantTags;
    }

    private List<String> getCafeTags() {
        List<String> cafeTags = new ArrayList<>();
        cafeTags.add("#베이커리");
        cafeTags.add("#카공");
        cafeTags.add("#대형 카페");
        // 필요에 따라 데이터를 추가합니다.
        return cafeTags;
    }

    private List<String> getActivityTags() {
        List<String> activityTags = new ArrayList<>();
        activityTags.add("#산책");
        activityTags.add("#영화 / 공연 관람");
        activityTags.add("#방탈출 카페");
        activityTags.add("#보드게임 카페");
        activityTags.add("#대형 카페");
        // 필요에 따라 데이터를 추가합니다.
        return activityTags;
    }

    private void getRecommendation() {
        List<Map<String, Object>> restaurantResults = new ArrayList<>();
        List<Map<String, Object>> cafeResults = new ArrayList<>();
        List<Map<String, Object>> activityResults = new ArrayList<>();

        // Fetch restaurants
        db.collection("restaurants").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> data = document.getData();
                    List<String> tags = (List<String>) data.get("tags");
                    if (tags != null && !tags.isEmpty()) {
                        for (String tag : selectedRestaurantTags) {
                            if (tags.contains(tag)) {
                                restaurantResults.add(data);
                                break;
                            }
                        }
                    }
                }

                // Fetch cafes
                db.collection("cafes").get().addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task2.getResult()) {
                            Map<String, Object> data = document.getData();
                            List<String> tags = (List<String>) data.get("tags");
                            if (tags != null && !tags.isEmpty()) {
                                for (String tag : selectedCafeTags) {
                                    if (tags.contains(tag)) {
                                        cafeResults.add(data);
                                        break;
                                    }
                                }
                            }
                        }

                        // Fetch activities
                        db.collection("activity").get().addOnCompleteListener(task3 -> {
                            if (task3.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task3.getResult()) {
                                    Map<String, Object> data = document.getData();
                                    List<String> tags = (List<String>) data.get("tags");
                                    if (tags != null && !tags.isEmpty()) {
                                        for (String tag : selectedActivityTags) {
                                            if (tags.contains(tag)) {
                                                activityResults.add(data);
                                                break;
                                            }
                                        }
                                    }
                                }

                                // Get random recommendation from each category
                                Map<String, Object> recommendedRestaurant = getRandomItem(restaurantResults);
                                Map<String, Object> recommendedCafe = getRandomItem(cafeResults);
                                Map<String, Object> recommendedActivity = getRandomItem(activityResults);

                                // Pass the recommendation to the result activity
                                Intent intent = new Intent(TagActivity.this, ResultActivity.class);
                                intent.putExtra("recommendedRestaurant", convertMapToHashMap(recommendedRestaurant));
                                intent.putExtra("recommendedCafe", convertMapToHashMap(recommendedCafe));
                                intent.putExtra("recommendedActivity", convertMapToHashMap(recommendedActivity));
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

    private Map<String, Object> getRandomItem(List<Map<String, Object>> items) {
        if (items.isEmpty()) return null;
        Random random = new Random();
        return items.get(random.nextInt(items.size()));
    }

    private HashMap<String, Object> convertMapToHashMap(Map<String, Object> map) {
        if (map == null) return null;
        HashMap<String, Object> hashMap = new HashMap<>(map);
        Object location = map.get("location");
        if (location instanceof GeoPoint) {
            GeoPoint geoPoint = (GeoPoint) location;
            hashMap.put("latitude", geoPoint.getLatitude());
            hashMap.put("longitude", geoPoint.getLongitude());
            hashMap.remove("location");
        }
        return hashMap;
    }
}
