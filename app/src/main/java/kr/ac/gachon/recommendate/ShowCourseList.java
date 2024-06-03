package kr.ac.gachon.recommendate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ShowCourseList extends Activity {

    public ListView courseListView;
    RankListAdapter adapter;
    String[] keyword, like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_courselist);

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("");


        // 뒤로가기 버튼 설정
        Button btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료, 이전 화면으로 돌아감
            }
        });

        // 1. 테스트를 위해 array.xml 파일에서 임시로 list 가져오기
        keyword = getResources().getStringArray(R.array.rank_test_keyword);
        like = getResources().getStringArray(R.array.rank_test_like);

        // list에 나타낼 화면에서 id로 참조하기
        courseListView = findViewById(R.id.course_listView);
        adapter = new RankListAdapter();

        for(int i = 0; i < keyword.length; i++){
            adapter.addItem(new RankList(keyword[i], like[i]));
        }
        courseListView.setAdapter(adapter);

        // 2. 각 코스 클릭시 세부사항 화면으로 이동
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 클릭한 해당 글의 keyword와 like수를 호출
                String key = ((RankList)adapter.getItem(position)).getKey();
                String like = ((RankList)adapter.getItem(position)).getLike();

                // CourseDetail.class로 보내기
                Intent intent = new Intent(ShowCourseList.this, CourseDetail.class);
                // key, like 값 보내기
                intent.putExtra("key", key);
                intent.putExtra("likeNum", like);

                startActivity(intent);

            }
        });

    }
}
