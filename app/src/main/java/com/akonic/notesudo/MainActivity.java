package com.akonic.notesudo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    Toolbar toolbar;
    RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    Adapter adapter;
    TextView noItemText;
    private ActionBarDrawerToggle actionBarDrawerToggle;
  //  private AppBarConfiguration mAppBarConfiguration;
    SimpleDatabase simpleDatabase;
    ImageView scan,add_notes,bg;
    EditText e;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //setSupportActionBar(toolbar);
        noItemText = findViewById(R.id.noItemText);
        scan = findViewById(R.id.scanimage);
        add_notes = (ImageView) findViewById(R.id.addnotes);
        bg = findViewById(R.id.bg);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(MainActivity.this, Ocr.class);

                startActivity(i);
            }
        });
        add_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddNote.class);
                Toast.makeText(getBaseContext(), "Add New Note", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

        button=findViewById(R.id.btndrp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // hideKeyboard(drawerLayout);

                if(!drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.openDrawer(GravityCompat.START);
                else drawerLayout.closeDrawer(GravityCompat.END);
            }
        });


            simpleDatabase = new SimpleDatabase(this);
        List<Note> allNotes = simpleDatabase.getAllNotes();
        recyclerView = findViewById(R.id.allNotesList);

        if (allNotes.isEmpty()) {
            noItemText.setVisibility(View.VISIBLE);
            bg.setVisibility(View.VISIBLE);

        } else {
            noItemText.setVisibility(View.GONE);
            bg.setVisibility(View.GONE);
            displayList(allNotes);
        }
        //adding a TextChangedListener
        //to call a method whenever there is some change on the EditText

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();


                if (id == R.id.bank) {
                    startActivity(new Intent(MainActivity.this, Ocr.class));
                } else if (id == R.id.ngo) {
                    startActivity(new Intent(MainActivity.this, AddNote.class));
                } else if (id == R.id.devFragment) {
                    Intent i=new Intent(android.content.Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject test");
                    i.putExtra(android.content.Intent.EXTRA_TEXT, "extra text that you want to put");
                    startActivity(Intent.createChooser(i,"Share via"));
                } else if (id == R.id.signFragment) {
                    startActivity(new Intent(MainActivity.this, Ocr.class));
                }

                return true;
            }

        });
    }



    private void displayList(List<Note> allNotes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,allNotes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Note> getAllNotes = simpleDatabase.getAllNotes();
        if(getAllNotes.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
            bg.setVisibility(View.VISIBLE);

        }else {
            noItemText.setVisibility(View.GONE);
            bg.setVisibility(View.GONE);
            displayList(getAllNotes);
        }


    }

}
