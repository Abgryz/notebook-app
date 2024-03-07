package com.example.notebook;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebook.adapter.TaskAdapter;
import com.example.notebook.model.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final List<Task> tasks = new ArrayList<>();
    private RecyclerView taskRecycler;
    private TextInputLayout textInputLayout;
    private Button addTaskButton;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        taskRecycler = findViewById(R.id.taskRecycler);
        textInputLayout = findViewById(R.id.textInput);
        addTaskButton = findViewById(R.id.addTaskButton);

        dummyTaskList();
        setTaskRecycler();
        taskAdder();
    }

    private void dummyTaskList() {
        tasks.add(new Task(0, "task1", false));
        tasks.add(new Task(1, "task2", true));
        tasks.add(new Task(2, "task3", false));
        tasks.add(new Task(3, "task4", false));
        tasks.add(new Task(4, "task5", false));
        tasks.add(new Task(5, "task6", false));
        tasks.add(new Task(6, "task7", true));
        tasks.add(new Task(7, "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong text", true));
        tasks.add(new Task(8, "task9", true));
        tasks.add(new Task(9, "task10", false));
        tasks.add(new Task(10, "task11", false));
        tasks.add(new Task(11, "task12", true));
        tasks.add(new Task(12, "task13", true));
        tasks.add(new Task(13, "task14", false));
        tasks.add(new Task(14, "task15", true));
        tasks.add(new Task(15, "task16", false));
    }

    private void setTaskRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        taskRecycler.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(this, tasks);
        taskRecycler.setAdapter(taskAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void taskAdder(){
        addTaskButton.setOnClickListener(v -> {
            EditText editText = textInputLayout.getEditText();
            if (editText != null){
                String taskText = editText.getText().toString();
                if (!TextUtils.isEmpty(taskText)){
                    Task newTask = new Task(tasks.size(), taskText, false);
                    tasks.add(newTask);

                    editText.setText("");
                    taskAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Input task text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}