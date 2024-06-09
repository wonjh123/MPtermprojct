package kr.ac.gachon.recommendate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TagToggleAdapter extends RecyclerView.Adapter<TagToggleAdapter.TagViewHolder> {

    private List<String> tagList;
    private List<String> selectedTags;

    public TagToggleAdapter(List<String> tagList, List<String> selectedTags) {
        this.tagList = tagList;
        this.selectedTags = selectedTags;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        String tag = tagList.get(position);
        holder.tagText.setText(tag);

        if (selectedTags.contains(tag)) {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.holo_blue_light));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.transparent));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTags.contains(tag)) {
                    selectedTags.remove(tag);
                    holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.transparent));
                } else {
                    selectedTags.add(tag);
                    holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.holo_blue_light));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    class TagViewHolder extends RecyclerView.ViewHolder {
        TextView tagText;

        TagViewHolder(@NonNull View itemView) {
            super(itemView);
            tagText = itemView.findViewById(R.id.tag_text);
        }
    }
}
