package kr.ac.gachon.recommendate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ShowCourseList extends Activity {

    public ListView courseListView;
    RankListAdapter adapter;
    String[] keyword, like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_courselist);

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

    }
}
