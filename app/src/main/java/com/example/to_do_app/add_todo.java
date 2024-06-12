package com.example.to_do_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.to_do_app.dbModels.CategoriesModel;
import com.example.to_do_app.dbModels.ToDoModle;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class add_todo extends AppCompatActivity {
    private Spinner spinnerCategory;
    private Button button;

    private EditText input;

    public int category_id;

    private Db db;

    private Toolbar toolbar;

    private  List<CategoriesModel> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_todo);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        button = findViewById(R.id.buttonSave);
        input = findViewById(R.id.editTextTitle);
        db= new Db(add_todo.this);

        categories = db.getCategories();


        toolbar=findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(view -> {
            // Handle the navigation icon click here
            startActivity(new Intent(this,MainActivity.class));
        });

button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        LocalDateTime now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }
        String formattedNow = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedNow = now.format(formatter);
        } ;
        int category = (int) spinnerCategory.getSelectedItemPosition();
        String t = String.valueOf(input.getText().toString().trim());
        ToDoModle todo = new ToDoModle(-1,t, categories.get(category).getId(),formattedNow,false);

        if(t.isEmpty()){
            Toast.makeText(add_todo.this,"Title is Required",Toast.LENGTH_SHORT).show();
            return;
        }

        //if(spinnerCategory.getSelectedItemPosition()){
        //    Toast.makeText(add_todo.this,"Category is Required",Toast.LENGTH_SHORT).show();
          //  return;
        //}


//        Db Db = new Db(add_todo.this);
        boolean isSuccess =db.addTodo(todo);
        if(isSuccess){
            input.setText("");
        }
        Toast.makeText(add_todo.this,"Success ="+ isSuccess,Toast.LENGTH_SHORT).show();

    }
});



//        categories.add("Option 1");
//        categories.add("Option 2");
//        categories.add("Option 3");
//        categories.add("Option 3");
//        categories.add("Option 3");

        List<String> options = new ArrayList<>();

        for (CategoriesModel item : categories) {
            options.add(item.getCategory());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(dataAdapter);
    }
}