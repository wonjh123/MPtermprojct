package kr.ac.gachon.recommendate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class LastDatesCourseDetail extends AppCompatActivity {

    private static final String TAG = "LastDatesCourseDetail";

    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private String courseName;
    private TextView courseNameText, restaurantNameText, cafeNameText, activityNameText;
    private TextView restaurantTagsText, cafeTagsText, activityTagsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);  // 주신 레이아웃 파일 이름을 사용해주세요

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("지난 데이트 상세");

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
        courseName = getIntent().getStringExtra("courseName");
        Log.d(TAG, "Received courseName: " + courseName);

        // Firestore에서 코스 상세 정보 가져오기
        fetchCourseDetails();
    }

    private void fetchCourseDetails() {
        if (currentUser != null) {
            db.collection("users")
                    .document(currentUser.getUid())
                    .collection("LastDates")
                    .whereEqualTo("name", courseName)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Map<String, Object> data = document.getData();

                            String courseName = (String) data.get("name");
                            Map<String, Object> restaurant = (Map<String, Object>) data.get("recommendedRestaurant");
                            Map<String, Object> cafe = (Map<String, Object>) data.get("recommendedCafe");
                            Map<String, Object> activity = (Map<String, Object>) data.get("recommendedActivity");

                            courseNameText.setText(courseName);

                            if (restaurant != null) {
                                restaurantNameText.setText("Restaurant: " + restaurant.get("name"));
                                List<String> restaurantTags = (List<String>) restaurant.get("tags");
                                if (restaurantTags != null) {
                                    restaurantTagsText.setText("Tags: " + String.join(", ", restaurantTags));
                                } else {
                                    restaurantTagsText.setText("Tags: N/A");
                                }
                            } else {
                                restaurantNameText.setText("Restaurant: N/A");
                                restaurantTagsText.setText("Tags: N/A");
                            }

                            if (cafe != null) {
                                cafeNameText.setText("Cafe: " + cafe.get("name"));
                                List<String> cafeTags = (List<String>) cafe.get("tags");
                                if (cafeTags != null) {
                                    cafeTagsText.setText("Tags: " + String.join(", ", cafeTags));
                                } else {
                                    cafeTagsText.setText("Tags: N/A");
                                }
                            } else {
                                cafeNameText.setText("Cafe: N/A");
                                cafeTagsText.setText("Tags: N/A");
                            }

                            if (activity != null) {
                                activityNameText.setText("Activity: " + activity.get("name"));
                                List<String> activityTags = (List<String>) activity.get("tags");
                                if (activityTags != null) {
                                    activityTagsText.setText("Tags: " + String.join(", ", activityTags));
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
}
