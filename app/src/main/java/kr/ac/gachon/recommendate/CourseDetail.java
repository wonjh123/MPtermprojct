package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CourseDetail extends AppCompatActivity {

    private Intent intent;
    private TextView keyText, likeText, textContainer;
    private String keyword, like;
    private String[] DBKey, DBLike, DBtext, DBUser;
    private Button likeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);

        // 1. ShowCourseList.class에서 코스 클릭 시 putExtra로 보낸 자료 받기 위해 intent 초기화
        intent = getIntent();
        keyText = findViewById(R.id.show_keyword);
        likeText = findViewById(R.id.like_num);
        textContainer = findViewById(R.id.text_container);

        // putExtra로 보낸 값 받아오기
        keyword = intent.getStringExtra("key");
        like = intent.getStringExtra("likeNum");
        keyText.setText(keyword);
        likeText.setText(like);

        // 2. 해당 keyword와 like에 해당하는 글 DB에서 불러오기
        /*
        * Db 부분
        * */

        // 화면 테스트를 위해 임시로 array.xml 파일에서 keyword, like 가져와서 비교하는 식으로 진행

        DBKey = getResources().getStringArray(R.array.rank_test_keyword);
        DBLike = getResources().getStringArray(R.array.rank_test_like);
        DBtext = getResources().getStringArray(R.array.rank_test_txt);
        // DBUser = getResources().getStringArray(R.array.rank_test_user);

        for(int i = 0; i < DBKey.length; i++){
            if (DBKey[i] == keyword && DBLike[i] == like){
                textContainer.setText(DBtext[i]);
            }
        }


    }
}
