package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    private Button btnOpenRecommend;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // btn_open_recommend 버튼을 찾습니다.
        btnOpenRecommend = view.findViewById(R.id.btn_open_recommend);

        // btn_open_recommend 버튼에 클릭 리스너를 설정합니다.
        btnOpenRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // RecommendActivity로 이동하는 인텐트 생성
                Intent intent = new Intent(getActivity(), RecommendActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}