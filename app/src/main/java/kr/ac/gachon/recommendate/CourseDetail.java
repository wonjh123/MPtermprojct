package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class CourseDetail extends AppCompatActivity {

    private static final String TAG = "CourseDetail";

    private FirebaseFirestore db;
    private String courseName;
    private TextView courseNameText, restaurantNameText, cafeNameText, activityNameText;
    private TextView restaurantTagsText, cafeTagsText, activityTagsText;
    private TextView headerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);

        db = FirebaseFirestore.getInstance();

        // 뒤로가기 버튼 설정
        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(v -> finish());

        courseNameText = findViewById(R.id.course_name);
        restaurantNameText = findViewById(R.id.restaurant_name);
        restaurantTagsText = findViewById(R.id.restaurant_tags);
        cafeNameText = findViewById(R.id.cafe_name);
        cafeTagsText = findViewById(R.id.cafe_tags);
        activityNameText = findViewById(R.id.activity_name);
        activityTagsText = findViewById(R.id.activity_tags);

        // 인텐트로 전달된 courseName 받아오기
        Intent intent = getIntent();
        courseName = intent.getStringExtra("courseName");
        Log.d(TAG, "Received courseName: " + courseName);

        // Firestore에서 코스 상세 정보 가져오기
        fetchCourseDetails();
    }

    private void fetchCourseDetails() {
        db.collection("courses")
                .whereEqualTo("name", courseName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> data = document.getData();

                        String courseName = (String) data.get("name");

                        Map<String, Object> restaurant = (Map<String, Object>) data.get("restaurant");
                        Map<String, Object> cafe = (Map<String, Object>) data.get("cafe");
                        Map<String, Object> activity = (Map<String, Object>) data.get("activity");

                        courseNameText.setText(courseName);

                        if (restaurant != null) {
                            restaurantNameText.setText((String)restaurant.get("name"));
                            List<String> restaurantTags = (List<String>) restaurant.get("tags");
                            if (restaurantTags != null) {
                                restaurantTagsText.setText(String.join(", ", restaurantTags));
                            } else {
                                restaurantTagsText.setText("Tags: N/A");
                            }
                        } else {
                            restaurantNameText.setText("Restaurant: N/A");
                            restaurantTagsText.setText("Tags: N/A");
                        }

                        if (cafe != null) {
                            cafeNameText.setText((String)cafe.get("name"));
                            List<String> cafeTags = (List<String>) cafe.get("tags");
                            if (cafeTags != null) {
                                cafeTagsText.setText(String.join(", ", cafeTags));
                            } else {
                                cafeTagsText.setText("Tags: N/A");
                            }
                        } else {
                            cafeNameText.setText("Cafe: N/A");
                            cafeTagsText.setText("Tags: N/A");
                        }

                        if (activity != null) {
                            activityNameText.setText((String)activity.get("name"));
                            List<String> activityTags = (List<String>) activity.get("tags");
                            if (activityTags != null) {
                                activityTagsText.setText(String.join(", ", activityTags));
                            } else {
                                activityTagsText.setText("Tags: N/A");
                            }
                        } else {
                            activityNameText.setText("Activity: N/A");
                            activityTagsText.setText("Tags: N/A");
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "get failed with ", e);
                });
    }
}
