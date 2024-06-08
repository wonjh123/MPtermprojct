package kr.ac.gachon.recommendate;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TagActivity extends AppCompatActivity {

    private boolean isToggleOn = false;
    private boolean isToggleOn2 = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("오늘의 분위기는 어떤가요?");

        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);

        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료, 이전 화면으로 돌아감
            }
        });

        Button btnGetResult = findViewById(R.id.btn_get_result);
        btnGetResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TagActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });

        List<String> data1 = getDataFromDatabase();
        List<String> data2 = getDataFromDatabase();
        List<String> data3 = getDataFromDatabase();

        // 첫 번째 RecyclerView 설정
        RecyclerView recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TagToggleAdapter adapter1 = new TagToggleAdapter(data1);
        recyclerView1.setAdapter(adapter1);

        // 두 번째 RecyclerView 설정
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TagToggleAdapter adapter2 = new TagToggleAdapter(data2);
        recyclerView2.setAdapter(adapter2);

        // 세 번째 RecyclerView 설정
        RecyclerView recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TagToggleAdapter adapter3 = new TagToggleAdapter(data3);
        recyclerView3.setAdapter(adapter3);
    }
    private List<String> getDataFromDatabase() {
        List<String> data = new ArrayList<>();
        data.add("Button 1");
        data.add("Button 2");
        data.add("Button 3");
        // 필요에 따라 데이터를 추가합니다.
        return data;
    }
}
