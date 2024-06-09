package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyPageFragment extends Fragment {
    private Button btnLastDate; // ID 변경에 따른 변수명 수정
    private Button btnMyTags;
    private Button btnLogout;
    private FirebaseAuth mAuth;
    private TextView textUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize buttons
        btnLastDate = view.findViewById(R.id.btn_last_date); // ID 변경에 맞게 수정
        btnMyTags = view.findViewById(R.id.btn_my_tags);
        btnLogout = view.findViewById(R.id.btn_logout); // 로그아웃 버튼 초기화
        textUserName = view.findViewById(R.id.text_user_name);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            loadUserInfo(user.getUid());
        }

        // Set listeners for buttons
        btnLastDate.setOnClickListener(new View.OnClickListener() { // 변수명 변경에 맞게 수정
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LastDatesActivity.class);
                startActivity(intent);
            }
        });

        btnMyTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyTagActivity.class);
                startActivity(intent);
            }
        });

        // Set listener for logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut(); // 로그아웃
                Toast.makeText(getActivity(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginPage.class); // 로그인 화면으로 이동
                startActivity(intent);
                getActivity().finish(); // 현재 액티비티 종료
            }
        });

        return view;
    }

    private void loadUserInfo(String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String username = document.getString("name");
                                textUserName.setText(username);
                            } else {
                                Toast.makeText(getActivity(), "No such document", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "get failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
