package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class CourseDetail extends AppCompatActivity {

    private Intent intent;
    private TextView keyText, likeText, textContainer;
    private String keyword, like;
    private String[] DBKey, DBLike, DBtext, DBUser;
    private Button likeBtn;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);

        db = FirebaseFirestore.getInstance();

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("데이트");

        // 뒤로가기 버튼 설정
        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료, 이전 화면으로 돌아감
            }
        });

        // 1. ShowCourseList.class에서 코스 클릭 시 putExtra로 보낸 자료 받기 위해 intent 초기화
        intent = getIntent();
        keyText = findViewById(R.id.show_keyword);
        likeText = findViewById(R.id.like_num);
        textContainer = findViewById(R.id.text_container);

        // putExtra로 보낸 값 받아오기
        keyword = intent.getStringExtra("key");
        like = intent.getStringExtra("likeNum");
        keyText.setText(keyword);
        likeText.setText(like);

        // 2. 해당 keyword와 like에 해당하는 글 DB에서 불러오기
        /*
         * Db 부분
         * */

        // 화면 테스트를 위해 임시로 array.xml 파일에서 keyword, like 가져와서 비교하는 식으로 진행

        DBKey = getResources().getStringArray(R.array.rank_test_keyword);
        DBLike = getResources().getStringArray(R.array.rank_test_like);
        DBtext = getResources().getStringArray(R.array.rank_test_txt);
        // DBUser = getResources().getStringArray(R.array.rank_test_user);

        for (int i = 0; i < DBKey.length; i++) {
            if (DBKey[i].equals(keyword) && DBLike[i].equals(like)) {
                textContainer.setText(DBtext[i]);
            }
        }

        RecyclerView recyclerView = findViewById(R.id.date_item_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDateItemsFromFirestore(new FirestoreCallback() {
            @Override
            public void onCallback(List<Map<String, Object>> dateItems) {
                DateItemAdapter adapter = new DateItemAdapter(dateItems);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void fetchDateItemsFromFirestore(final FirestoreCallback callback) {
        db.collection("dateItems")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Map<String, Object>> dateItems = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            Map<String, Object> dateItem = new HashMap<>();
                            dateItem.put("name", data.get("name"));
                            dateItem.put("tags", data.get("tags"));
                            GeoPoint location = (GeoPoint) data.get("location");
                            if (location != null) {
                                dateItem.put("latitude", location.getLatitude());
                                dateItem.put("longitude", location.getLongitude());
                            }
                            dateItems.add(dateItem);
                        }
                        callback.onCallback(dateItems);
                    }
                });
    }

    private interface FirestoreCallback {
        void onCallback(List<Map<String, Object>> dateItems);
    }
}
