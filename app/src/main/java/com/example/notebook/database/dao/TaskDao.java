package com.example.notebook.database.dao;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.notebook.database.DBData;
import com.example.notebook.model.Task;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskDao implements Dao<Task> {
    private final SQLiteDatabase db;
    @Override
    public Task get(int id) {
        @SuppressLint("Recycle") Cursor cursor = db.query(
                DBData.TABLE_NAME,
                new String[] {DBData.KEY_ID, DBData.KEY_TASK, DBData.KEY_IS_COMPLETED},
                DBData.KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null
        );
        if (cursor != null){
            cursor.moveToFirst();
            return Task.builder()
                    .id(Integer.parseInt(cursor.getString(0)))
                    .task(cursor.getString(1))
                    .isCompleted((Integer.parseInt(cursor.getString(2))) == 1)
                    .build();
        }
        return null;
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();

        String query = String.format("select * from %s", DBData.TABLE_NAME);
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Log.i(TAG, String.format("getAll: 0: %s, 1: %s, 2: %s",
                        cursor.getString(0), cursor.getString(1),
                        cursor.getString(2)
                ));

                Task task = Task.builder()
                        .id(Integer.parseInt(cursor.getString(0)))
                        .task(cursor.getString(1))
                        .isCompleted((Integer.parseInt(cursor.getString(2))) == 1)
                        .build();
                tasks.add(task);
                Log.i(TAG, "getAll: " + task);
            } while (cursor.moveToNext());
        }
        return tasks;
    }

    @Override
    public boolean create(Task task) {
        ContentValues values = new ContentValues();
        values.put(DBData.KEY_TASK, task.getTask());
        values.put(DBData.KEY_IS_COMPLETED, task.isCompleted());

        db.insert(DBData.TABLE_NAME, null, values);
        return true;
    }

    @Override
    public boolean update(Task task) {
        ContentValues values = new ContentValues();
        values.put(DBData.KEY_TASK, task.getTask());
        values.put(DBData.KEY_IS_COMPLETED, task.isCompleted());

        String whereClause = DBData.KEY_ID + "=?";
        String[] whereArgs = {String.valueOf(task.getId())};
        int rowsAffected = db.update(DBData.TABLE_NAME, values, whereClause, whereArgs);
        return rowsAffected > 0;
    }

    @Override
    public boolean delete(int id) {
        String whereClause = DBData.KEY_ID + "=?";
        String[] whereArgs = {String.valueOf(id)};
        int rowsAffected = db.delete(DBData.TABLE_NAME, whereClause, whereArgs);

        return rowsAffected > 0;
    }
}
