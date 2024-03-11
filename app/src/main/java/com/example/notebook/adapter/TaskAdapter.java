package com.example.notebook.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebook.R;
import com.example.notebook.database.DatabaseHandler;
import com.example.notebook.model.Task;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private final List<Task> tasks;
    private final DatabaseHandler db;

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskItems = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(taskItems);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox taskCheckBox;
        private final ImageButton deleteButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskCheckBox = itemView.findViewById(R.id.taskCheckBox);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            taskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> taskCheckboxListener(isChecked));
            deleteButton.setOnClickListener(v -> deleteButtonListener());
        }

        private void deleteButtonListener() {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                db.deleteTask(tasks.remove(position).getId());
                notifyItemRemoved(position);
            }
        }

        private void taskCheckboxListener(boolean isChecked) {
            int position = getAdapterPosition();
            Task task = tasks.get(position);
            task.setCompleted(isChecked);
            db.updateTask(task);
            itemView.post(() -> notifyItemChanged(position));
        }

        public void bind(@NonNull Task task) {
            taskCheckBox.setText(task.getTask());
            taskCheckBox.setChecked(task.isCompleted());

            if (task.isCompleted()) {
                taskCheckBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                taskCheckBox.setPaintFlags(0);
            }
        }
    }
}
