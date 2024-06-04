package kr.ac.gachon.recommendate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RankListAdapter extends BaseAdapter {
    ArrayList<RankList> items = new ArrayList<>();
    Context context;
    Button likeBtn;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public void addItem(RankList item){
        items.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        context = parent.getContext();
        RankList listItem = items.get(position);

        // RankList의 리스트
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }

        // listview에 나타낼 data
        TextView keyword = convertView.findViewById(R.id.keyword);
        TextView like = convertView.findViewById(R.id.like);

        keyword.setText(listItem.getKey());
        like.setText(listItem.getLike());

        /*
        likeBtn = convertView.findViewById(R.id.like_btn);
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 클릭 시 DB like 수 증가하기

            }
        });
        */


        return convertView;
    }

}
