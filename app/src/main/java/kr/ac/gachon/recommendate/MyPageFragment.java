package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class MyPageFragment extends Fragment {
    private Button btnLastDate; // ID 변경에 따른 변수명 수정
    private Button btnMyTags;
    private Button btnLogout;
    private FirebaseAuth mAuth;

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
}
