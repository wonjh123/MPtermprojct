package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TagActivity extends AppCompatActivity {

    private boolean isToggleOn = false;
    private boolean isToggleOn2 = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("오늘의 분위기는 어떤가요?");

        Button btnArrowBack = findViewById(R.id.btn_arrow_back);

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

        Button btnTag2 = findViewById(R.id.btn_tag2);

        btnTag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 토글 상태 변경
                isToggleOn2 = !isToggleOn2;

                // 토글 상태에 따라 배경색 변경
                if (isToggleOn2) {
                    btnTag2.setBackgroundColor(Color.BLUE); // ON 상태 배경색
                } else {
                    btnTag2.setBackgroundColor(Color.RED); // OFF 상태 배경색
                }
            }
        });

        Button btnTag3 = findViewById(R.id.btn_tag3);

        btnTag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 토글 상태 변경
                isToggleOn = !isToggleOn;

                // 토글 상태에 따라 배경색 변경
                if (isToggleOn) {
                    btnTag3.setBackgroundColor(Color.BLUE); // ON 상태 배경색
                } else {
                    btnTag3.setBackgroundColor(Color.RED); // OFF 상태 배경색
                }
            }
        });
    }
}
