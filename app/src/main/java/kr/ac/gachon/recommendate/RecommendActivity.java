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

        // "Back to Home" 버튼을 찾습니다.
        btnBackToHome = findViewById(R.id.btn_back_to_home);

        // "Back to Home" 버튼에 클릭 리스너를 설정합니다.
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // HomeFragment로 이동하는 인텐트 생성
                Intent intent = new Intent(RecommendActivity.this, HomeFragment.class);
                startActivity(intent);
            }
        });
    }
}