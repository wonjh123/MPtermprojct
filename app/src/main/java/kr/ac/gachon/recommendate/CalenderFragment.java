package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class CalenderFragment extends Fragment {


    // 랭킹 top3 나타내기
    private ListView rankTop3;
    String[] keyword, like;
    public ListView rankList;
    RankListAdapter rankListAdapter;
    private Button likeBtn, otherBtn;
    private String keyItem, likeItem;

    public CalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        // Inflate the layout for this fragment

        // 1. DB에서 like 수가 많은 순서대로 정렬 및 상위 3개 가져오기
        // 2. 각 DB에서 keyword, like 쌍 읽어오기
        /*
        String[] 형태
        keyword =
        like =
         */

        // 2-1. Test: 화면 테스트를 위해 임시로 array.xml에 세팅해둔 top3 리스트를 이용함.
        // db연결 후 삭제 초지
        keyword = getResources().getStringArray(R.array.rank_test_keyword);
        like = getResources().getStringArray(R.array.rank_test_like);

        // 3. ListView, Adapter 설정
        rankList = view.findViewById(R.id.rank_top3);
        rankListAdapter = new RankListAdapter();

        // top 3, 상위 3개의 keyword, like 수 list에 추가하기
        for(int i = 0 ; i < 3; i++){
            rankListAdapter.addItem(new RankList(keyword[i],like[i]));
        }
        rankList.setAdapter(rankListAdapter);

        // 3-1. like button 클릭 시 DB의 like +1

        // 3-2. 해당 코스 클릭 시 게시글 화면으로 이동
        rankList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                keyItem = ((RankList)rankListAdapter.getItem(position)).getKey();
                likeItem = ((RankList)rankListAdapter.getItem(position)).getLike();

                Intent intent = new Intent(getActivity(), CourseDetail.class);
                intent.putExtra("key", keyItem);
                intent.putExtra("likeNum", like);

                startActivity(intent);

            }
        });

        // 4. 다른 코스 보기 Button 클릭 시 이동
        otherBtn = view.findViewById(R.id.other_btn);
        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowCourseList.class);
                startActivity(intent);
            }
        });

        return view;
    }
}