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
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LastDatesActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<LastDate> lastDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_dates);

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("지난 데이트");

        Button btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료, 이전 화면으로 돌아감
            }
        });

        listView = findViewById(R.id.list_last_dates);
        lastDates = DataProvider.getLastDates();

        LastDateAdapter adapter = new LastDateAdapter(getApplicationContext(), lastDates);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LastDate selectedDate = lastDates.get(position); // 클릭된 아이템 가져오기
                Intent intent = new Intent(LastDatesActivity.this, CourseDetail.class); // 다음 액티비티로 전환할 인텐트 생성
                /*intent.putExtra("leftText", selectedDate.getLeftText()); // 왼쪽 텍스트 데이터 추가
                intent.putExtra("rightText", selectedDate.getRightText()); // 오른쪽 텍스트 데이터 추가*/
                startActivity(intent); // 다음 액티비티로 전환
            }
        });
    }

    public static class LastDate {
        String leftText;
        String rightText;

        public LastDate(String leftText, String rightText) {
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

    public static class LastDateAdapter extends ArrayAdapter<LastDate> {

        private final Context context;
        private final List<LastDatesActivity.LastDate> dates;

        public LastDateAdapter(Context context, List<LastDatesActivity.LastDate> dates) {
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

            TextView leftTextView = convertView.findViewById(R.id.left_text1);
            TextView rightTextView = convertView.findViewById(R.id.right_text1);

            LastDatesActivity.LastDate date = dates.get(position);
            leftTextView.setText(date.getLeftText());
            rightTextView.setText(date.getRightText());

            return convertView;
        }
    }
}