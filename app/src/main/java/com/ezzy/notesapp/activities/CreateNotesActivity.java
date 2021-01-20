package com.ezzy.notesapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ezzy.notesapp.R;

public class CreateNotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        ImageView imageBackButton = findViewById(R.id.imageBack);
        imageBackButton.setOnClickListener(v -> onBackPressed());
    }
}