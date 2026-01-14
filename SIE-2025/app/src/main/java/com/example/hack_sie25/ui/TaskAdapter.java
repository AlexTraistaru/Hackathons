package com.example.hack_sie25.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hack_sie25.R;
import com.example.hack_sie25.Database.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.VH> {

    public interface OnClick { void onTaskClick(Task t); }

    private final List<Task> items = new ArrayList<>();
    private final OnClick onClick;

    public TaskAdapter(OnClick onClick){ this.onClick = onClick; }

    public void setItems(List<Task> data){
        items.clear();
        if(data!=null) items.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        Task t = items.get(pos);
        h.title.setText(t.title);
        h.status.setText(t.status);
        h.itemView.setOnClickListener(v -> { if(onClick!=null) onClick.onTaskClick(t); });
    }

    @Override public int getItemCount(){ return items.size(); }

    static class VH extends RecyclerView.ViewHolder{
        TextView title, status;
        VH(View v){ super(v); title = v.findViewById(R.id.tTitle); status = v.findViewById(R.id.tStatus); }
    }
}
