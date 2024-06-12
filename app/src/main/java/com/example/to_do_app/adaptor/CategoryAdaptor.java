package com.example.to_do_app.adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.to_do_app.Db;
import com.example.to_do_app.MainActivity;
import com.example.to_do_app.R;
import com.example.to_do_app.categories;
import com.example.to_do_app.dbModels.CategoriesModel;
import com.example.to_do_app.to_dos;

import java.util.List;

public class CategoryAdaptor extends BaseAdapter {
    private Context context;
    private List<CategoriesModel> items;

    private Db db;

    private FragmentManager fragmentManager;
    private  LayoutInflater inflater;


    public  CategoryAdaptor(Context context, List<CategoriesModel> items, Db db, FragmentManager fragmentManager,LayoutInflater inflater) {
        this.context = context;
        this.items = items;
        this.db = db;
        this.fragmentManager = fragmentManager;
        this.inflater = inflater;
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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic for item click

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("cat_id", currentItem.getId());
                intent.putExtra("fragment", "todos");
                intent.putExtra("title2", currentItem.getCategory());
                context.startActivity(intent);
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.frame_layout, to_dos.newInstance("To dos",""));
//                transaction.addToBackStack(null);
//                transaction.commit();
//
//                getSupportActionBar().setTitle("title");

//                Toast.makeText(context, "Item clicked: " + currentItem.getId(), Toast.LENGTH_SHORT).show();


            }
        });

        dropdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //  Toast.makeText(context,"Edit ",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                View dialogView = inflater.inflate(R.layout.edit_category, null);
                builder.setView(dialogView);

                EditText editTitle = dialogView.findViewById(R.id.CateditTitle);

                Button btnSave = dialogView.findViewById(R.id.btnSaveCat);

                editTitle.setText(currentItem.getCategory());


                AlertDialog dialog = builder.create();
                btnSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String newTitle = editTitle.getText().toString();


                        currentItem.setCategory(newTitle);



                        db.updateCat(currentItem);
                        items.set(position, currentItem);

                        notifyDataSetChanged();
                        dialog.dismiss();
                    }

                });
                dialog.show();
            }
        });

        return convertView;
    }





}