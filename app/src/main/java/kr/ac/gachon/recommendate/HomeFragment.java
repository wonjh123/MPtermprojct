package kr.ac.gachon.recommendate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Button btnOpenRecommend;
    private ListView listView;
    private ArrayList<RecommendDate> recommendDates;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        btnOpenRecommend = rootView.findViewById(R.id.btn_open_recommend);

        btnOpenRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // RecommendActivity로 이동하는 인텐트 생성
                Intent intent = new Intent(getActivity(), RecommendActivity.class);
                startActivity(intent);
            }
        });

        listView = rootView.findViewById(R.id.list_recommend_dates);

        recommendDates = DataProvider.getRecommendDates();

        RecommendDateAdapter adapter = new RecommendDateAdapter(getActivity(), recommendDates);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeFragment.RecommendDate selectedDate = recommendDates.get(position); // 클릭된 아이템 가져오기
                Intent intent = new Intent(getActivity(), CourseDetail.class); // 다음 액티비티로 전환할 인텐트 생성
                startActivity(intent); // 다음 액티비티로 전환
            }
        });


        return rootView;
    }

    public static class RecommendDate {
        String leftText;
        String rightText;

        public RecommendDate(String leftText, String rightText) {
            this.leftText = leftText;
            this.rightText = rightText;
        }

        public String getLeftText() {
            return leftText;
        }

        public String getRightText() {
            return rightText;
        }
    }

    public static class RecommendDateAdapter extends ArrayAdapter<RecommendDate> {

        private final Context context;
        private final List<RecommendDate> dates;

        public RecommendDateAdapter(Context context, List<RecommendDate> dates) {
            super(context, R.layout.home_recommend_item, dates);
            this.context = context;
            this.dates = dates;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.custom_listview, parent, false);
            }

            /*TextView leftTextView = convertView.findViewById(R.id.keyword);
            TextView rightTextView = convertView.findViewById(R.id.right_text);

            RecommendDate date = dates.get(position);
            leftTextView.setText(date.getLeftText());
            rightTextView.setText(date.getRightText());*/

            return convertView;
        }
    }

}