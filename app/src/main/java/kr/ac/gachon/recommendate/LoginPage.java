package kr.ac.gachon.recommendate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    private Button signInBtn, registerBtn;
    private Button findPW;
    private EditText email_EditText, pw_EditText;
    private String emailText, pwText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        // 1. email / pw 입력 받기
        // email, pw 입력받기
        email_EditText = findViewById(R.id.enter_email);
        pw_EditText = findViewById(R.id.enter_pw);

        emailText = email_EditText.getText().toString();
        pwText = pw_EditText.getText().toString();

        // 2. 로그인 버튼 클릭시 db 확인
        signInBtn = findViewById(R.id.sign_btn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 입력받은 emailText, pwText DB와 확인

                // 1. 만약 email이 DB에 없다면,
                 /*
                    Toast.makeText(getApplicationContext(),
                    "해당 계정이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                */

                // 2. email이 존재하고, pw가 DB와 다를 때,
                /*
                    Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.",
                    Toast.LENGTH_LONG).show();
                */

                // 3. email, pw가 db와 모두 일치할 때
                /*
                Intent intent = new Intent(getApplicationContext(),
                        NaviActivity.class);
                        startActivity(intent);
                 */
            }
        });

        // 3. 회원가입 버튼 클릭 이벤트
        registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(getApplicationContext(),
                        RegisterPopup.class);
                startActivity(intent);
                 */
            }
        });

        // 4. pw 찾기 클릭 시 해당 페이지 이동
        findPW = findViewById(R.id.find_pw_btn);
        findPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(getApplicationContext(),
                        FindPWPage.class);
                startActivity(intent);
                 */
            }
        });


        // 5. 구글 로그인


    }
}
