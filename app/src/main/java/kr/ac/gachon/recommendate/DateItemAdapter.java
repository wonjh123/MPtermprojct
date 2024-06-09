package kr.ac.gachon.recommendate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class DateItemAdapter extends RecyclerView.Adapter<DateItemAdapter.DateItemViewHolder> {

    private List<Map<String, Object>> dateItemList;

    public DateItemAdapter(List<Map<String, Object>> dateItemList) {
        this.dateItemList = dateItemList;
    }

    @NonNull
    @Override
    public DateItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateItemViewHolder holder, int position) {
        Map<String, Object> item = dateItemList.get(position);
        holder.dateItemName.setText(item.get("name").toString());
        holder.dateItemTags.setText("Tags: " + item.get("tags").toString());
        holder.dateItemLocation.setText("Location: " + item.get("latitude") + ", " + item.get("longitude"));
    }

    @Override
    public int getItemCount() {
        return dateItemList.size();
    }

    static class DateItemViewHolder extends RecyclerView.ViewHolder {
        TextView dateItemName;
        TextView dateItemTags;
        TextView dateItemLocation;

        DateItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dateItemName = itemView.findViewById(R.id.date_item_name);
            dateItemTags = itemView.findViewById(R.id.date_item_tags);
            dateItemLocation = itemView.findViewById(R.id.date_item_location);
        }
    }
}
