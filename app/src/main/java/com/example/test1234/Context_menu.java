package com.example.test1234;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.test1234.Services.DeleteTaskAsync;
import com.example.test1234.Services.GetCommentsAsync;

import java.util.ArrayList;

public class Context_menu extends AppCompatActivity {

    Button delete;
    Button addComment;
    Button showDetails;
   // Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu);

        DisplayMetrics dm= new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;


        getWindow().setLayout((int) (width*.8), (int) (height*.6));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(220,220,220)));

        addComment = (Button) findViewById(R.id.comment);
        delete = (Button) findViewById(R.id.delete);
        showDetails = (Button) findViewById(R.id.details);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("id");
                new DeleteTaskAsync(Context_menu.this, id).execute();
            }
        });

        showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Title = getIntent().getStringExtra("title");
                String Description= getIntent().getStringExtra("description");
                String id = getIntent().getStringExtra("id");
                int selectedId = Integer.parseInt(id);
                String startDate= getIntent().getStringExtra("startDate");
                String endDate= getIntent().getStringExtra("endDate");

                new GetCommentsAsync(Context_menu.this, null, selectedId, Description, Title, startDate, endDate).execute();
            }
        });

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Context_menu.this, AddComment.class);
                String id = getIntent().getStringExtra("id");
                intent.putExtra("id", id);
                Context_menu.this.startActivity(intent);
            }
        });
    }
}