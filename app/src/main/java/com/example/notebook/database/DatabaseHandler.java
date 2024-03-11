package com.example.notebook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.notebook.database.dao.TaskDao;
import com.example.notebook.model.Task;

import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, DBData.DATABASE_NAME, null, DBData.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTaskTable = String.format("create table %s (%s integer primary key autoincrement, %s text not null, %s boolean default false);",
                DBData.TABLE_NAME, DBData.KEY_ID, DBData.KEY_TASK, DBData.KEY_IS_COMPLETED);

        db.execSQL(createTaskTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("drop table if exists %s", DBData.DATABASE_NAME));
        onCreate(db);
    }

    public void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        new TaskDao(db).create(task);
        db.close();
    }

    public Task getTask(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Task task = new TaskDao(db).get(id);
        db.close();
        return task;
    }

    public List<Task> getAllTasks(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> tasks = new TaskDao(db).getAll();
        db.close();
        return tasks;
    }

    public void deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        new TaskDao(db).delete(id);
        db.close();
    }

    public void updateTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        new TaskDao(db).update(task);
        db.close();
    }
}
