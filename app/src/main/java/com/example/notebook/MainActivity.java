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
import com.example.notebook.database.DatabaseHandler;
import com.example.notebook.model.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final DatabaseHandler db = new DatabaseHandler(this);
    private List<Task> tasks;
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

        tasks = db.getAllTasks();
        setTaskRecycler();
        taskAdder();
    }

    private void setTaskRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        taskRecycler.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(this, tasks, db);
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
                    db.addTask(newTask);

                    editText.setText("");
                    taskAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Input task text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}