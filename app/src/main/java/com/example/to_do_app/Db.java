package com.example.to_do_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.to_do_app.dbModels.CategoriesModel;
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

    public static final String TABLE_CATEGORIES = "categories";
    public static final String COLUMN_CATEGORY_ID_FOR_CATEGORY_TABEL = "id";
    public static final String COLUMN_CATEGORY_NAME = "category";
//    public static final String COLUMN_CREATED_AT = "created_at";

    private static final String TABLE_CATEGORIES_CREATE =
            "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                    COLUMN_CATEGORY_ID_FOR_CATEGORY_TABEL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CATEGORY_NAME + " TEXT , "+ COLUMN_CREATED_AT +" DATETIME );";
    public Db(@Nullable Context context) {
        super(context, TODODB, null,5);
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
        db.execSQL(TABLE_CATEGORIES_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODOTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        onCreate(db);
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

    public List<ToDoModle> getTodos(String[] param){
        String sql;
        if(param[0].equals("Add dos")){
            sql = "SELECT * from "+TODOTABLE +" where "+COLUMN_ISDONE  +" != 1";
        }
        else if(param[0].equals("Finished")){
            sql = "SELECT * from "+TODOTABLE +" where "+COLUMN_ISDONE  +" = 1";
        }
        else if(param[0].equals("get_todo_in_cat")){
            sql = "SELECT * from "+TODOTABLE +" where "+COLUMN_CATEGORY_ID  +" = "+param[1];
        }
        else {
            sql = "SELECT * from "+TODOTABLE;
        }

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




    public List<CategoriesModel> getCategories(){
        String sql = "SELECT * from "+TABLE_CATEGORIES;
        List<CategoriesModel> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do {
                int cid = cursor.getInt(0);

                String category = cursor.getString(1);
                String createdAt = cursor.getString(2);

                CategoriesModel ct = new CategoriesModel(cid,category,createdAt);
                returnList.add(ct);
            }while (cursor.moveToNext());
        };
        cursor.close();
        db.close();
        return returnList;
    }




    public boolean addCategory(CategoriesModel cat){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_CATEGORY_NAME,cat.getCategory());
        cv.put(COLUMN_CREATED_AT,cat.getCreated_at());


        long insert = db.insert(TABLE_CATEGORIES, null, cv);

        if(insert == -1){
            return  false;
        }
        return true;
    }



    public void updateTodo(ToDoModle todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODOS, todo.getTodo());
        values.put(COLUMN_CATEGORY_ID, todo.getCategoryId());

        db.update(TODOTABLE, values, "id = ?", new String[]{String.valueOf(todo.getId())});
        db.close();
    }



    public void updateCat(CategoriesModel cat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, cat.getCategory());


        db.update(TABLE_CATEGORIES, values, "id = ?", new String[]{String.valueOf(cat.getId())});
        db.close();
    }

    public void updateTodoStatus(int id, boolean isDone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("is_done", isDone ? 1 : 0);

        db.update(TODOTABLE, values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
