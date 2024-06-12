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
import com.example.to_do_app.R;
import com.example.to_do_app.dbModels.CategoriesModel;

import java.util.List;

public class CategoryAdaptor extends BaseAdapter {
    private Context context;
    private List<CategoriesModel> items;

    private Db db;


    public  CategoryAdaptor(Context context, List<CategoriesModel> items,Db db) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.catgegory_item, parent, false);
        }

        CategoriesModel currentItem = (CategoriesModel) getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.category_text);
        TextView descriptionTextView = convertView.findViewById(R.id.category_time);
        ImageButton dropdownButton = convertView.findViewById(R.id.categorydeleteButton);



        titleTextView.setText(currentItem.getCategory());

        descriptionTextView.setText(currentItem.getCreated_at());


        dropdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(context,"Edit ",Toast.LENGTH_SHORT).show();
            }});
        return convertView;
    }
}