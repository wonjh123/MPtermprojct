package kr.ac.gachon.recommendate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LastDatesActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<LastDate> lastDates;
    private LastDateAdapter adapter;

    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_dates);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("지난 데이트");

        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(v -> finish());

        listView = findViewById(R.id.list_last_dates);
        lastDates = new ArrayList<>();
        adapter = new LastDateAdapter(getApplicationContext(), lastDates);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            LastDate selectedDate = lastDates.get(position);
            Intent intent = new Intent(LastDatesActivity.this, LastDatesCourseDetail.class);
            intent.putExtra("courseName", selectedDate.getName());
            startActivity(intent);
        });

        fetchLastDates();
    }

    private void fetchLastDates() {
        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid())
                    .collection("LastDates")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("name");
                                LastDate lastDate = new LastDate(name);
                                lastDates.add(lastDate);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
        }
    }

    public static class LastDate {
        String name;

        public LastDate(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class LastDateAdapter extends ArrayAdapter<LastDate> {

        private final Context context;
        private final List<LastDate> dates;

        public LastDateAdapter(Context context, List<LastDate> dates) {
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

            TextView leftTextView = convertView.findViewById(R.id.keyword);
            LastDate date = dates.get(position);
            leftTextView.setText(date.getName());

            return convertView;
        }
    }
}
