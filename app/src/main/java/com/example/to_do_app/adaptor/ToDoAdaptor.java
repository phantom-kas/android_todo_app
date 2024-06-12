package com.example.to_do_app.adaptor;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.to_do_app.Db;
import com.example.to_do_app.MainActivity;
import com.example.to_do_app.R;
import com.example.to_do_app.dbModels.ToDoModle;

import java.util.List;
public class ToDoAdaptor extends BaseAdapter {
    private Context context;
    private List<ToDoModle> items;

    private Db db;


    public  ToDoAdaptor(Context context, List<ToDoModle> items,Db db) {
        this.context = context;
        this.items = items;
        this.db = db;
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
                            items.remove(position);
                            notifyDataSetChanged();
                            return true;
                        }
                        else if(item.getItemId() == R.id.option2){
                            Toast.makeText(context, "Edit selected", Toast.LENGTH_SHORT).show();
                            items.remove(position);
                            notifyDataSetChanged();
                            return true;
                        }
                       return false;
                    }
                });
                popupMenu.show();
            }});
        return convertView;
    }
}