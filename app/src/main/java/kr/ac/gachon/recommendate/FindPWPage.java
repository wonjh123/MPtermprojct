package kr.ac.gachon.recommendate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FindPWPage extends Activity {

    private EditText email_editText, name_editText;
    String email, name;
    private TextView setMessage;
    private Button checkBtn, backToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_pw);

        // 1. email, name 입력란에 입력된 문자 가져오기
        email_editText = findViewById(R.id.enter_email);
        name_editText = findViewById(R.id.enter_name);
        email = email_editText.getText().toString();
        name = name_editText.getText().toString();

        setMessage = findViewById(R.id.set_view);

        // 2. 확인 버튼 클릭 시 email 확인
        checkBtn = findViewById(R.id.check_btn);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. email, name이 firebase DB와 일치하는지 확인
                /*
                if email이 db에 없을 경우
                setMessage.setText("해당 계정이 존재하지 않습니다.");

                else if  email은 있으나 이름이 틀린 경우
                setMessage.setText("게정 사용자 이름이 틀립니다.");

                else if  email, name 모두 일치하는 경우
                setMessage.setText(pw내용 출력)
                 */
            }
        });

        // 3. 로그인 화면으로 돌아가기 버튼 클릭 시

        backToLoginBtn = findViewById(R.id.back_login_btn);
        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }
        });


    }
}
