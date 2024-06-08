package kr.ac.gachon.recommendate;

import android.app.Activity;
import android.content.Intent;
import android.hardware.camera2.CameraExtensionSession;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowCourseList extends Activity {

    public ListView courseListView;
    RankListAdapter adapter, content_adapter, cafe_adapter, food_adapter;
    String[] keyword, like;
    String[] content, cafe, food;
    String[] contentLike, cafeLike, foodLike;
    Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_courselist);

        TextView headerTitle = findViewById(R.id.text_header_title);
        headerTitle.setText("");


        // 뒤로가기 버튼 설정
        ImageButton btnArrowBack = findViewById(R.id.btn_arrow_back);
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료, 이전 화면으로 돌아감
            }
        });

        // 카테고리 설정
        String[] categoryList = {"활동 및 체험", "디저트 카페 위주", "맛집 위주"};
        category = findViewById(R.id.category);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter1);

        // test를 위해 array.xml에서 string array 가져오기

        // 전체 course 리스트
        keyword = getResources().getStringArray(R.array.rank_test_keyword);
        like = getResources().getStringArray(R.array.rank_test_like);

        content = getResources().getStringArray(R.array.활동);
        contentLike = getResources().getStringArray(R.array.활동_좋아요);
        cafe = getResources().getStringArray(R.array.카페);
        cafeLike = getResources().getStringArray(R.array.카페_좋아요);
        food = getResources().getStringArray(R.array.맛집);
        foodLike = getResources().getStringArray(R.array.맛집_좋아요);

        // list에 나타낼 화면에서 id로 참조하기
        courseListView = findViewById(R.id.course_listView);
        adapter = new RankListAdapter();
        content_adapter = new RankListAdapter();
        cafe_adapter = new RankListAdapter();
        food_adapter = new RankListAdapter();

        // 카데고리 선택 시 리스트 내용 보이기
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        for(int i = 0; i<content.length; i++){
                            content_adapter.addItem(new RankList(content[i], contentLike[i]));
                        }
                        courseListView.setAdapter(content_adapter);
                        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                // 클릭한 해당 글의 keyword와 like수를 호출
                                String key = ((RankList)content_adapter.getItem(position)).getKey();
                                String like = ((RankList)content_adapter.getItem(position)).getLike();

                                // CourseDetail.class로 보내기
                                Intent intent = new Intent(ShowCourseList.this, CourseDetail.class);
                                // key, like 값 보내기
                                intent.putExtra("key", key);
                                intent.putExtra("likeNum", like);

                                startActivity(intent);
                            }
                        });
                        break;

                    case 1:
                        for(int i = 0; i<cafe.length; i++){
                            cafe_adapter.addItem(new RankList(cafe[i], cafeLike[i]));
                        }
                        courseListView.setAdapter(cafe_adapter);
                        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                // 클릭한 해당 글의 keyword와 like수를 호출
                                String key = ((RankList)cafe_adapter.getItem(position)).getKey();
                                String like = ((RankList)cafe_adapter.getItem(position)).getLike();

                                // CourseDetail.class로 보내기
                                Intent intent = new Intent(ShowCourseList.this, CourseDetail.class);
                                // key, like 값 보내기
                                intent.putExtra("key", key);
                                intent.putExtra("likeNum", like);

                                startActivity(intent);

                            }
                        });
                        break;
                    case 2:
                        for(int i = 0; i<food.length; i++){
                            food_adapter.addItem(new RankList(food[i], foodLike[i]));
                        }
                        courseListView.setAdapter(food_adapter);
                        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                // 클릭한 해당 글의 keyword와 like수를 호출
                                String key = ((RankList)food_adapter.getItem(position)).getKey();
                                String like = ((RankList)food_adapter.getItem(position)).getLike();

                                // CourseDetail.class로 보내기
                                Intent intent = new Intent(ShowCourseList.this, CourseDetail.class);
                                // key, like 값 보내기
                                intent.putExtra("key", key);
                                intent.putExtra("likeNum", like);

                                startActivity(intent);

                            }
                        });
                        break;
                    default:
                        for(int i = 0; i < keyword.length; i++){
                            adapter.addItem(new RankList(keyword[i], like[i]));
                        }
                        courseListView.setAdapter(adapter);
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
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // 2. 각 코스 클릭시 세부사항 화면으로 이동
        /*
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

         */

    }
}
