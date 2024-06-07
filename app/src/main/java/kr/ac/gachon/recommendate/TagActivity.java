package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

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

        ToggleButton toggleButton = findViewById(R.id.toggle_button);
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // isChecked가 true이면 버튼이 체크된 상태입니다.
            if (isChecked) {
                // TODO: 버튼이 체크된 상태일 때의 동작
            } else {
                // TODO: 버튼이 체크되지 않은 상태일 때의 동작
            }
        });

    }
}
