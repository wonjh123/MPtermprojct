package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RecommendActivity extends AppCompatActivity {

    private Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        // 뒤로가기 버튼 설정
        Button btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료, 이전 화면으로 돌아감
            }
        });

        Button btnChooseLocation = findViewById(R.id.btn_choose_location);
        btnChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TagActivity로 이동하는 Intent 생성
                Intent intent = new Intent(RecommendActivity.this, TagActivity.class);
                startActivity(intent); // TagActivity 시작
            }
        });
    }
}