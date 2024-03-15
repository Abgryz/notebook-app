package com.example.notebook.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;


import com.example.notebook.R;
import com.example.notebook.database.DatabaseHandler;
import com.example.notebook.model.Task;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditDialog extends DialogFragment {
    private Task task;
    private Context context;
    private DatabaseHandler db;
    private DialogCloseListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_dialog, container, false);

        EditText editText = view.findViewById(R.id.editTaskText);
        editText.setText(task.getTask());

        ImageButton confirmButton = view.findViewById(R.id.confirmButton);
        ImageButton cancelButton = view.findViewById(R.id.cancelButton);

        confirmButton.setOnClickListener(v -> {
            try {
                String newTitle = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(newTitle)){
                    task.setTask(newTitle);
                    db.updateTask(task);
                } else {
                    throw new NullPointerException();
                }

                listener.onCloseDialog();
                dismiss();
            } catch (NullPointerException e){
                Toast.makeText(context, "Input task text", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(v -> dismiss());
        return view;
    }
}