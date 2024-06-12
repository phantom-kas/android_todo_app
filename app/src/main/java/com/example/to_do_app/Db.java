package com.example.to_do_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.to_do_app.dbModels.ToDoModle;

import java.util.ArrayList;
import java.util.List;

public class Db extends SQLiteOpenHelper {

    public static final String COLUMN_TODOS = "todos";
    public static final String TODODB = "TODOLIST.db";

    private static final String COLUMN_ID= "id";

    public static final String TODOTABLE = "todo";

    public static final String COLUMN_ISDONE = "is_done";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_CATEGORY_ID = "category_id";

    private static final String TABLE_CATEGORIES_CREATE =
            "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                    COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CATEGORY_NAME + " TEXT);";
    public Db(@Nullable Context context) {
        super(context, TODODB, null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String sql = "CREATE TABLE " + TODOTABLE + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CREATED_AT + " DATETIME NOT NULL, "
            + COLUMN_TODOS + " TEXT NOT NULL, "
            + COLUMN_ISDONE+ " BOOL ,"
            + COLUMN_CATEGORY_ID + " INTEGER )";

    db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addTodo(ToDoModle td){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TODOS,td.getTodo());
        cv.put(COLUMN_CREATED_AT,td.getCreatedAt());
        cv.put(COLUMN_CATEGORY_ID,td.getCategoryId());
        cv.put(COLUMN_ISDONE,false);

        long insert = db.insert(TODOTABLE, null, cv);

        if(insert == -1){
            return  false;
        }
        return true;
    }

    public List<ToDoModle> getTodos(){
        String sql = "SELECT * from "+TODOTABLE;
        List<ToDoModle> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do {
                int tdID = cursor.getInt(0);
                String createdAt = cursor.getString(1);
                String todo = cursor.getString(2);
                boolean isdone = cursor.getInt(3) == 1;
                int categoryId = cursor.getInt(4);

                ToDoModle td = new ToDoModle(tdID,todo,categoryId,createdAt,isdone);
                returnList.add(td);
            }while (cursor.moveToNext());
            };
        cursor.close();
        db.close();
        return returnList;
        }


        public boolean deleteTodo(ToDoModle td){

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM "+TODOTABLE+" WHERE "+ COLUMN_ID+" ="+" "+td.getId();

        Cursor cursor = db.rawQuery(sql,null);
            return cursor.moveToFirst();
        }





}
