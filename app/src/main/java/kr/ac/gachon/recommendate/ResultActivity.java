package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("오늘의 데이트");


        // 뒤로가기 버튼 설정
        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료, 이전 화면으로 돌아감
            }
        });

        Button btnSaveResult = findViewById(R.id.btn_save_result);
        btnSaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, NaviActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finishAffinity();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.date_item_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DateItemAdapter adapter = new DateItemAdapter(getDateItemFromDatabase());
        recyclerView.setAdapter(adapter);
    }


    private List<String> getDateItemFromDatabase() {
        List<String> date_items = new ArrayList<>();
        date_items.add("Item 1");
        date_items.add("Item 2");
        date_items.add("Item 3");
        // 필요에 따라 데이터를 추가합니다.
        return date_items;
    }
}