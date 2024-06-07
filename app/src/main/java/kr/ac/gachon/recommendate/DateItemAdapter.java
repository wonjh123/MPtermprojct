package kr.ac.gachon.recommendate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DateItemAdapter extends RecyclerView.Adapter<DateItemAdapter.DateItemViewHolder> {

    private List<String> date_items;

    public DateItemAdapter(List<String> date_items) {
        this.date_items = date_items;
    }

    @NonNull
    @Override
    public DateItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item_list, parent, false);
        return new DateItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateItemViewHolder holder, int position) {
        // Example data binding - replace with actual data binding logic
        holder.nameTextView.setText(date_items.get(position));
        holder.locationTextView.setText("Location " + position);

        // Set up the tags RecyclerView (assuming you want tags for each item)
        List<String> tags = getTagsForItem(position);
        TagTextAdapter tagAdapter = new TagTextAdapter(tags);
        holder.tagsRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        holder.tagsRecyclerView.setAdapter(tagAdapter);
    }

    @Override
    public int getItemCount() {
        return date_items.size();
    }

    static class DateItemViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        TextView nameTextView;
        TextView locationTextView;
        RecyclerView tagsRecyclerView;

        public DateItemViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photo);
            nameTextView = itemView.findViewById(R.id.name);
            locationTextView = itemView.findViewById(R.id.location);
            tagsRecyclerView = itemView.findViewById(R.id.tagsRecyclerView);
        }
    }

    private List<String> getTagsForItem(int position) {
        // Example tag data - replace with actual data logic
        List<String> tags = new ArrayList<>();
        tags.add("Tag A");
        tags.add("Tag B");
        tags.add("Tag C");
        return tags;
    }
}
