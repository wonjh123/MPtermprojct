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

        List<String> restaurantTags = getRestaurantTags();

        // 첫 번째 RecyclerView 설정
        RecyclerView restaurantRecyclerView = findViewById(R.id.recyclerView1);
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        TagToggleAdapter restaurantAdaptor = new TagToggleAdapter(restaurantTags);
        restaurantRecyclerView.setAdapter(restaurantAdaptor);


        List<String> cafeTags = getCafeTags();
        // 두 번째 RecyclerView 설정
        RecyclerView cafeRecyclerView = findViewById(R.id.recyclerView2);
        cafeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        TagToggleAdapter cafeAdaptor = new TagToggleAdapter(cafeTags);
        cafeRecyclerView.setAdapter(cafeAdaptor);

        List<String> activityTags = getActivityTags();
        // 세 번째 RecyclerView 설정
        RecyclerView activityRecyclerView = findViewById(R.id.recyclerView3);
        activityRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        TagToggleAdapter activityAdaptor = new TagToggleAdapter(activityTags);
        activityRecyclerView.setAdapter(activityAdaptor);
    }

    private List<String> getRestaurantTags() {
        List<String> restaurantTags = new ArrayList<>();
        restaurantTags.add("한식");
        restaurantTags.add("중식");
        restaurantTags.add("일식");
        restaurantTags.add("양식");
        // 필요에 따라 데이터를 추가합니다.
        return restaurantTags;
    }

    private List<String> getCafeTags() {
        List<String> cafeTags = new ArrayList<>();
        cafeTags.add("베이커리");
        cafeTags.add("카공");
        cafeTags.add("대형 카페");
        // 필요에 따라 데이터를 추가합니다.
        return cafeTags;
    }

    private List<String> getActivityTags() {
        List<String> cafeTags = new ArrayList<>();
        cafeTags.add("산책");
        cafeTags.add("영화 / 공연 관람");
        cafeTags.add("방탈출 카페");
        cafeTags.add("보드게임 카페");
        cafeTags.add("대형 카페");
        // 필요에 따라 데이터를 추가합니다.
        return cafeTags;
    }
}
