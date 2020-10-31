package com.example.test1234;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.test1234.Services.AddCommentAsync;

public class AddComment extends AppCompatActivity {

    Button save;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        save = findViewById(R.id.addComment);
        text = findViewById(R.id.inputComment);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("id");
                String txt = text.getText().toString();
                new AddCommentAsync(AddComment.this, id, txt).execute();
            }
        });
    }
}