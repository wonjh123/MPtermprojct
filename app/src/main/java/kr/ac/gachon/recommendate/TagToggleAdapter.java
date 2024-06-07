package kr.ac.gachon.recommendate;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TagToggleAdapter extends RecyclerView.Adapter<TagToggleAdapter.TagViewHolder> {

    private List<String> data;

    public TagToggleAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_toggle_item, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        // 데이터 바인딩
        String text = data.get(position);
        holder.toggleButton.setText(text);
        holder.toggleButton.setTextOn(text);
        holder.toggleButton.setTextOff(text);

        // 토글 버튼 클릭 리스너 설정
        holder.toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ToggleButton) v).isChecked()) {
                    holder.toggleButton.setTextColor(Color.MAGENTA);
                } else {
                    holder.toggleButton.setTextColor(Color.GRAY);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class TagViewHolder extends RecyclerView.ViewHolder {
        ToggleButton toggleButton;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            toggleButton = itemView.findViewById(R.id.toggleButton);
        }
    }
}
