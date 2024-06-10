package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("오늘의 분위기는 어떤가요?");

        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(v -> finish());

        Button btnGetResult = findViewById(R.id.btn_get_result);
        btnGetResult.setOnClickListener(v -> getRecommendation());

        setupRecyclerView(R.id.recyclerView1, getRestaurantTags(), selectedRestaurantTags);
        setupRecyclerView(R.id.recyclerView2, getCafeTags(), selectedCafeTags);
        setupRecyclerView(R.id.recyclerView3, getActivityTags(), selectedActivityTags);
    }

    private void setupRecyclerView(int recyclerViewId, List<String> tags, List<String> selectedTags) {
        RecyclerView recyclerView = findViewById(recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TagToggleAdapter adapter = new TagToggleAdapter(tags, selectedTags);
        recyclerView.setAdapter(adapter);
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
                    if (tags != null && !tags.isEmpty() && containsAny(tags, selectedRestaurantTags)) {
                        restaurantResults.add(data);
                    }
                }

                // Fetch cafes
                db.collection("cafes").get().addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task2.getResult()) {
                            Map<String, Object> data = document.getData();
                            List<String> tags = (List<String>) data.get("tags");
                            if (tags != null && !tags.isEmpty() && containsAny(tags, selectedCafeTags)) {
                                cafeResults.add(data);
                            }
                        }

                        // Fetch activities
                        db.collection("activity").get().addOnCompleteListener(task3 -> {
                            if (task3.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task3.getResult()) {
                                    Map<String, Object> data = document.getData();
                                    List<String> tags = (List<String>) data.get("tags");
                                    if (tags != null && !tags.isEmpty() && containsAny(tags, selectedActivityTags)) {
                                        activityResults.add(data);
                                    }
                                }

                                // Get random recommendation from each category
                                Map<String, Object> recommendedRestaurant = getRandomItem(restaurantResults);
                                Map<String, Object> recommendedCafe = getRandomItem(cafeResults);
                                Map<String, Object> recommendedActivity = getRandomItem(activityResults);

                                // Pass the recommendation to the result activity
                                Intent intent = new Intent(TagActivity.this, ResultActivity.class);
                                putRecommendationIntoIntent(intent, "recommendedRestaurant", recommendedRestaurant);
                                putRecommendationIntoIntent(intent, "recommendedCafe", recommendedCafe);
                                putRecommendationIntoIntent(intent, "recommendedActivity", recommendedActivity);
                                startActivity(intent);
                            } else {
                                Log.e("TagActivity", "Error getting documents: ", task3.getException());
                            }
                        });
                    } else {
                        Log.e("TagActivity", "Error getting documents: ", task2.getException());
                    }
                });
            } else {
                Log.e("TagActivity", "Error getting documents: ", task.getException());
            }
        });
    }

    private boolean containsAny(List<String> tags, List<String> selectedTags) {
        for (String tag : selectedTags) {
            if (tags.contains(tag)) {
                return true;
            }
        }
        return false;
    }

    private Map<String, Object> getRandomItem(List<Map<String, Object>> items) {
        if (items.isEmpty()) return null;
        Random random = new Random();
        return items.get(random.nextInt(items.size()));
    }

    private void putRecommendationIntoIntent(Intent intent, String key, Map<String, Object> recommendation) {
        if (recommendation != null) {
            intent.putExtra(key + "Name", (String) recommendation.get("name"));
            intent.putStringArrayListExtra(key + "Tags", (ArrayList<String>) recommendation.get("tags"));
            GeoPoint location = (GeoPoint) recommendation.get("location");
            intent.putExtra(key + "Latitude", location.getLatitude());
            intent.putExtra(key + "Longitude", location.getLongitude());
        }
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
            holder.itemView.setSelected(selectedTags.contains(tag));

            holder.itemView.setOnClickListener(v -> {
                if (selectedTags.contains(tag)) {
                    selectedTags.remove(tag);
                    holder.itemView.setSelected(false);
                } else {
                    selectedTags.add(tag);
                    holder.itemView.setSelected(true);
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
