package kr.ac.gachon.recommendate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends Activity {

    private EditText emailEditText, nameEditText, pwEditText, checkEditText;
    private Button regis_btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_popup);

        mAuth = FirebaseAuth.getInstance();

        // 1. email, name, pw 작성
        emailEditText = findViewById(R.id.enter_email);
        nameEditText = findViewById(R.id.enter_name);
        pwEditText = findViewById(R.id.enter_pw);
        checkEditText = findViewById(R.id.enter_check);

        // 2. 등록 버튼 클릭 시
        regis_btn = findViewById(R.id.register_btn);
        regis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = emailEditText.getText().toString().trim();
                String nameText = nameEditText.getText().toString().trim();
                String pwText = pwEditText.getText().toString().trim();
                String checkText = checkEditText.getText().toString().trim();

                if (pwText.equals(checkText)) {
                    registerUser(emailText, pwText, nameText);
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registerUser(String email, String password, String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Display name 업데이트
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();
                                user.updateProfile(profileUpdates);

                                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "회원가입 실패: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
