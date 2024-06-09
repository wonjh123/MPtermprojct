package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTagActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    private List<String> selectedRestaurantTags = new ArrayList<>();
    private List<String> selectedCafeTags = new ArrayList<>();
    private List<String> selectedActivityTags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tag);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("내 태그");

        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        Button btnSaveMyTags = findViewById(R.id.btn_tags_save);
        btnSaveMyTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTagsToFirestore();
                Intent intent = new Intent(MyTagActivity.this, NaviActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    private void saveTagsToFirestore() {
        if (currentUser != null) {
            Map<String, Object> userTags = new HashMap<>();
            userTags.put("restaurantTags", selectedRestaurantTags);
            userTags.put("cafeTags", selectedCafeTags);
            userTags.put("activityTags", selectedActivityTags);

            db.collection("users").document(currentUser.getUid())
                    .set(userTags, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> {
                        // 태그 저장 성공 시 처리할 작업
                    })
                    .addOnFailureListener(e -> {
                        // 태그 저장 실패 시 처리할 작업
                    });
        }
    }

    private List<String> getRestaurantTags() {
        List<String> restaurantTags = new ArrayList<>();
        restaurantTags.add("#한식");
        restaurantTags.add("#중식");
        restaurantTags.add("#일식");
        restaurantTags.add("#양식");
        return restaurantTags;
    }

    private List<String> getCafeTags() {
        List<String> cafeTags = new ArrayList<>();
        cafeTags.add("#베이커리");
        cafeTags.add("#카공");
        cafeTags.add("#대형 카페");
        return cafeTags;
    }

    private List<String> getActivityTags() {
        List<String> activityTags = new ArrayList<>();
        activityTags.add("#산책");
        activityTags.add("#영화 / 공연 관람");
        activityTags.add("#방탈출 카페");
        activityTags.add("#보드게임 카페");
        activityTags.add("#대형 카페");
        return activityTags;
    }

    private class TagToggleAdapter extends RecyclerView.Adapter<TagToggleAdapter.TagViewHolder> {

        private List<String> tagList;
        private List<String> selectedTags;

        TagToggleAdapter(List<String> tagList, List<String> selectedTags) {
            this.tagList = tagList;
            this.selectedTags = selectedTags;
        }

        @NonNull
        @Override
        public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false);
            return new TagViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
            String tag = tagList.get(position);
            holder.tagText.setText(tag);

            if (selectedTags.contains(tag)) {
                holder.itemView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            } else {
                holder.itemView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedTags.contains(tag)) {
                        selectedTags.remove(tag);
                        holder.itemView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    } else {
                        selectedTags.add(tag);
                        holder.itemView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return tagList.size();
        }

        class TagViewHolder extends RecyclerView.ViewHolder {
            TextView tagText;

            TagViewHolder(@NonNull View itemView) {
                super(itemView);
                tagText = itemView.findViewById(R.id.tag_text);
            }
        }
    }
}
