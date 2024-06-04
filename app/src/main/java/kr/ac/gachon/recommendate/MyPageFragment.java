package kr.ac.gachon.recommendate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class MyPageFragment extends Fragment {
    private Button btnLastDates;
    private Button btnMyTags;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        btnLastDates = view.findViewById(R.id.btn_last_date);

        btnLastDates.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LastDatesActivity.class);
                startActivity(intent);
            }
        });

        btnMyTags = view.findViewById(R.id.btn_my_tags);

        btnMyTags.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyTagActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}