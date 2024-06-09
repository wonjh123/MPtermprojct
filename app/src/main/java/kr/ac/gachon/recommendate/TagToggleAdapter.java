package kr.ac.gachon.recommendate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

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
        holder.toggleButton.setText(tag);
        holder.toggleButton.setTextOn(tag);
        holder.toggleButton.setTextOff(tag);

        if (selectedTags.contains(tag)) {
            holder.toggleButton.setChecked(true);
//            holder.toggleButton.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.holo_blue_light));
        } else {
            holder.toggleButton.setChecked(false);
//            holder.toggleButton.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.transparent));
        }

        holder.toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTags.contains(tag)) {
                    selectedTags.remove(tag);
//                    holder.toggleButton.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.transparent));
                } else {
                    selectedTags.add(tag);
//                    holder.toggleButton.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.holo_blue_light));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    class TagViewHolder extends RecyclerView.ViewHolder {
        ToggleButton toggleButton;

        TagViewHolder(@NonNull View itemView) {
            super(itemView);
            toggleButton = itemView.findViewById(R.id.toggleButton);
        }
    }
}
