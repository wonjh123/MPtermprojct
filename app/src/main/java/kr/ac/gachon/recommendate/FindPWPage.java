package kr.ac.gachon.recommendate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FindPWPage extends Activity {

    private EditText email_editText, name_editText;
    private String email, name;
    private TextView setMessage;
    private Button checkBtn, backToLoginBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_pw);

        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료, 이전 화면으로 돌아감
            }
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // 1. email, name 입력란에 입력된 문자 가져오기
        email_editText = findViewById(R.id.enter_email);
        name_editText = findViewById(R.id.enter_name);

        setMessage = findViewById(R.id.set_view);

        // 2. 확인 버튼 클릭 시 email 확인
        checkBtn = findViewById(R.id.check_btn);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_editText.getText().toString().trim();
                name = name_editText.getText().toString().trim();
                findPassword(email, name);
            }
        });
    }

    private void findPassword(String email, String name) {
        db.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("name", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            // 이메일과 이름이 일치하는 경우
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String password = document.getString("password");
                            if (password != null && password.length() > 2) {
                                String maskedPassword = maskPassword(password);
                                Toast.makeText(getApplicationContext(), "비밀번호 힌트: " + maskedPassword, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "비밀번호 힌트를 생성할 수 없습니다.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            // 이메일 또는 이름이 일치하지 않는 경우
                            Toast.makeText(getApplicationContext(), "계정이 존재하지 않거나 이메일이 일치하지 않습니다.", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private String maskPassword(String password) {
        if (password.length() <= 2) {
            return password;
        }
        int length = password.length();
        StringBuilder masked = new StringBuilder();
        masked.append(password.charAt(0));
        for (int i = 1; i < length - 1; i++) {
            masked.append('*');
        }
        masked.append(password.charAt(length - 1));
        return masked.toString();
    }
}
