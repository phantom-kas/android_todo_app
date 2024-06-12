package com.example.to_do_app.adaptor;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.to_do_app.Db;
import com.example.to_do_app.MainActivity;
import com.example.to_do_app.R;
import com.example.to_do_app.dbModels.CategoriesModel;
import com.example.to_do_app.dbModels.ToDoModle;

import java.util.ArrayList;
import java.util.List;
public class ToDoAdaptor extends BaseAdapter {
    private Context context;
    private List<ToDoModle> items;

    private Db db;

    private  LayoutInflater inflater;

    private List<CategoriesModel> cats;


    public  ToDoAdaptor(Context context, List<ToDoModle> items,Db db ,LayoutInflater inflater) {
        this.context = context;
        this.items = items;
        this.db = db;
        this.inflater = inflater;
        cats = db.getCategories();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void removeItem(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position);
            notifyDataSetChanged();
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }


        ToDoModle currentItem = (ToDoModle) getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.textViewTitle);
        TextView descriptionTextView = convertView.findViewById(R.id.textViewDescription);
        ImageButton dropdownButton = convertView.findViewById(R.id.dropdownButton);

        CheckBox donecheck =convertView.findViewById(R.id.donecheck);


        donecheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentItem.setDone(isChecked);
            db.updateTodoStatus(currentItem.getId(), isChecked);

            removeItem(position);
        });

        titleTextView.setText(currentItem.getTodo());
        donecheck.setChecked(currentItem.isDone());
        descriptionTextView.setText(currentItem.getCreatedAt());


        dropdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, dropdownButton);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.todos_drop_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override

                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.option1){
                            Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();

                            db.deleteTodo(currentItem);
                            removeItem(position);
                            return true;
                        }
                        else if(item.getItemId() == R.id.option2){
                          //  Toast.makeText(context, "Edit selected", Toast.LENGTH_SHORT).show();
                            showEditDialog(currentItem,position,context);
                            return true;
//

                        }
                       return false;
                    }
                });
                popupMenu.show();
            }});

        return convertView;
    }

    private void showEditDialog(ToDoModle todo, int position,Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = inflater.inflate(R.layout.edit_todo_dialog, null);
        builder.setView(dialogView);

        EditText editTitle = dialogView.findViewById(R.id.editTitle);
        Spinner editCategory = dialogView.findViewById(R.id.editCategory);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        editTitle.setText(todo.getTodo());


        AlertDialog dialog = builder.create();


        List<String> options = new ArrayList<>();
        int spinnerPosition = 0;
        int index = 0;
        for (CategoriesModel item : cats) {
            options.add(item.getCategory());
            if(todo.getCategoryId() == item.getId()){
                spinnerPosition = index;
                //Toast.makeText(context, String.valueOf(index),Toast.LENGTH_LONG).show();
            }

            index++;
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCategory.setAdapter(dataAdapter);


        editCategory.setSelection(spinnerPosition);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newTitle = editTitle.getText().toString();
                int position = editCategory.getSelectedItemPosition();

                todo.setTodo(newTitle);
                todo.setCategoryId(cats.get(position).getId());


                db.updateTodo(todo);
                items.set(position, todo);

                notifyDataSetChanged();
                dialog.dismiss();
            }

        });
        dialog.show();
    }


}