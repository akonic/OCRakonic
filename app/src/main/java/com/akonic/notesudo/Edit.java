package com.akonic.notesudo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

public class Edit extends AppCompatActivity {
    Toolbar toolbar;
    EditText nTitle,nContent;
    Calendar c;
    String todaysDate;
    String currentTime;
    long nId;
    ImageView edit,back,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //  toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        Intent i = getIntent();
        nId = i.getLongExtra("ID",0);
        SimpleDatabase db = new SimpleDatabase(this);
        Note note = db.getNote(nId);
        edit=findViewById(R.id.sv);
        back=findViewById(R.id.bck);
        //   delete=findViewById(R.id.del);
        final String title = note.getTitle();
        String content = note.getContent();
        nTitle = findViewById(R.id.noteTitle);
        nContent = findViewById(R.id.noteDetails);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editnote();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
      /*  delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletenote();
            }
        });*/




        nTitle.setText(title);
        nContent.setText(content);

        // set current date and time
        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        Log.d("DATE", "Date: "+todaysDate);
        currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
        Log.d("TIME", "Time: "+currentTime);
    }

    private void deletenote() {
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    private void editnote() {
        Note note = new Note(nId,nTitle.getText().toString(),nContent.getText().toString(),todaysDate,currentTime);
        Log.d("EDITED", "edited: before saving id -> " + note.getId());
        SimpleDatabase sDB = new SimpleDatabase(getApplicationContext());
        long id = sDB.editNote(note);
        Log.d("EDITED", "EDIT: id " + id);
        goToMain();
        Toast.makeText(this, "Note Edited.", Toast.LENGTH_SHORT).show();
    }


    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }
    //  @Override
  /*  public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }*/

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sv){
            Note note = new Note(nId,nTitle.getText().toString(),nContent.getText().toString(),todaysDate,currentTime);
            Log.d("EDITED", "edited: before saving id -> " + note.getId());
            SimpleDatabase sDB = new SimpleDatabase(getApplicationContext());
            long id = sDB.editNote(note);
            Log.d("EDITED", "EDIT: id " + id);
            goToMain();
            Toast.makeText(this, "Note Edited.", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.delete){
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }


}