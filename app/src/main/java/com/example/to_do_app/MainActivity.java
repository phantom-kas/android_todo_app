package com.example.to_do_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
        DrawerLayout drawerLayout;
        NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.nav_view);
        //setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
navigationView.bringToFront();
        ActionBarDrawerToggle toggle =  new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
//        replaceFragment(new categories(),"");
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

           if( item.getItemId() == R.id.to_dos){
               replaceFragment(new to_dos(),"Add dos");
           }
           else if( item.getItemId() == R.id.to_categories){
               replaceFragment(new categories(),"Categories");
           }
           else if( item.getItemId() == R.id.to_finished){
               replaceFragment(new Fragment_finised(),"Finished");
           }  else if( item.getItemId() == R.id.add_todo){
               startActivity(new Intent(MainActivity.this,add_todo.class));

           }

        drawerLayout.closeDrawer(GravityCompat.START);


        return false;
    }

    private  void replaceFragment(Fragment fragment,String title){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}