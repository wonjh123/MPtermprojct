package kr.ac.gachon.recommendate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private Button btnOpenMap;
    private ImageView imgMap; // ImageView for the map
    private Button btnOpenRecommend;
    private ListView listView;
    private ArrayList<RecommendDate> recommendDates;
    private FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();

        String mapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=37.7749,-122.4194&zoom=12&size=400x400&key=AIzaSyBjJwQBmFDQhdP-nmBmw2JXDvl91OQE4EA";

        // 1. Map 열기 버튼 이벤트
        btnOpenMap = rootView.findViewById(R.id.btn_open_map);
        imgMap = rootView.findViewById(R.id.img_map);

        // Load the image in a background thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                URL url = new URL(mapUrl);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                // Create rounded bitmap drawable
                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), bmp);
                roundedDrawable.setCornerRadius(24); // Set corner radius
                roundedDrawable.setAntiAlias(true);

                // Set the image on the main thread
                getActivity().runOnUiThread(() -> {
                    imgMap.setImageDrawable(roundedDrawable);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnOpenMap.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LocationMap.class);
            startActivity(intent);
        });

        // 2. 추천 버튼
        btnOpenRecommend = rootView.findViewById(R.id.btn_open_recommend);
        btnOpenRecommend.setOnClickListener(v -> {
            // RecommendActivity로 이동하는 인텐트 생성
            Intent intent = new Intent(getActivity(), RecommendActivity.class);
            startActivity(intent);
        });

        listView = rootView.findViewById(R.id.list_recommend_dates);

        recommendDates = new ArrayList<>();
        RecommendDateAdapter adapter = new RecommendDateAdapter(getActivity(), recommendDates);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            RecommendDate selectedDate = recommendDates.get(position); // 클릭된 아이템 가져오기
            Intent intent = new Intent(getActivity(), CourseDetail.class); // 다음 액티비티로 전환할 인텐트 생성
            // 전달할 데이터 추가
            intent.putExtra("courseName", selectedDate.getName());
            startActivity(intent); // 다음 액티비티로 전환
        });

        loadRecommendDates();

        return rootView;
    }

    private void loadRecommendDates() {
        db.collection("courses").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String name = document.getString("name");
                    recommendDates.add(new RecommendDate(name));
                    Log.d(TAG, "Course Name: " + name);  // 로깅 추가
                }
                ((ArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
                Log.d(TAG, "Data loaded, total items: " + recommendDates.size());  // 로깅 추가
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }

    public static class RecommendDate {
        String name;

        public RecommendDate(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class RecommendDateAdapter extends ArrayAdapter<RecommendDate> {

        private final Context context;
        private final List<RecommendDate> dates;

        public RecommendDateAdapter(Context context, List<RecommendDate> dates) {
            super(context, R.layout.last_dates_list_item, dates);
            this.context = context;
            this.dates = dates;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.last_dates_list_item, parent, false);
            }

            TextView nameTextView = convertView.findViewById(R.id.keyword);
            RecommendDate date = dates.get(position);
            nameTextView.setText(date.getName());

            Log.d(TAG, "ListView item: " + date.getName());  // 로깅 추가

            return convertView;
        }
    }
}
