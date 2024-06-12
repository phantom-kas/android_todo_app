package com.example.to_do_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.to_do_app.dbModels.CategoriesModel;
import com.example.to_do_app.dbModels.ToDoModle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class add_category extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_category);


        toolbar=findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(view -> {
            // Handle the navigation icon click here
            startActivity(new Intent(this,MainActivity.class));
        });

        input = findViewById(R.id.cat_editTextTitle);

    }


    public void onClick(View view){
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
        }


        String category = String.valueOf(input.getText().toString().trim());
        CategoriesModel cat = new CategoriesModel(-1,category,formattedNow);

        if(category.isEmpty()){
            Toast.makeText(add_category.this,"Title is Required",Toast.LENGTH_SHORT).show();
            return;
        }



        Db Db = new Db(add_category.this);
        boolean isSuccess =Db.addCategory(cat);
        Toast.makeText(add_category.this,"Success ="+ isSuccess,Toast.LENGTH_SHORT).show();

    }
}