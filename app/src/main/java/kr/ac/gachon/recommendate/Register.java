package kr.ac.gachon.recommendate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

    private EditText emailEditText, nameEditText, pwEditText, checkEditText;
    private String emailText, nameText, pwText, checkText;
    private Button regis_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_popup);

        // 1. email, name, pw 작성
        emailEditText = findViewById(R.id.enter_email);
        nameEditText = findViewById(R.id.enter_name);
        pwEditText = findViewById(R.id.enter_pw);
        checkEditText = findViewById(R.id.enter_check);

        emailText = emailEditText.getText().toString();
        nameText = nameEditText.getText().toString();
        pwText = pwEditText.getText().toString();
        checkText = checkEditText.getText().toString();

        // 2. 등록 버튼 클릭 시
        regis_btn = findViewById(R.id.register_btn);
        regis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                // 1. pwText != checkText
                /*
                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다",
                        Toast.LENGTH_LONG).show();
                 */

                // 2. else : db에 저장
            }
        });
    }

}
