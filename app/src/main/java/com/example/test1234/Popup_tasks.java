package com.example.test1234;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test1234.Services.GetExecutorsAsync;


public class Popup_tasks extends Activity {
ListView listView;

     public String fullName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_tasks);

        DisplayMetrics dm= new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int id = getIntent().getIntExtra("id", 0);
//        GetExecutorsAsync fullName = new GetExecutorsAsync(this, listView, id);

        getWindow().setLayout((int) (width*.8), (int) (height*.6));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(220,220,220)));

 //       new GetExecutorsAsync(this, listView).execute();

        String Title = getIntent().getStringExtra("title");
        String Description= getIntent().getStringExtra("description");
        String  Text = getIntent().getStringExtra("comment");
        String full= getIntent().getStringExtra("fullName");
        String startDate= getIntent().getStringExtra("startDate");
        String endDate= getIntent().getStringExtra("endDate");

        TextView fullname= findViewById(R.id.executor);
        TextView title= findViewById(R.id.task);
        TextView description= findViewById(R.id.taskDescription);
        TextView text = findViewById(R.id.comment);
        TextView stDate = findViewById(R.id.txt_date1);
        TextView eDate = findViewById(R.id.txt_date2);

        text.setText(Text);
        fullname.setText(full);
        title.setText(Title);
        description.setText(Description);
        stDate.setText(startDate);
        eDate.setText(endDate);


        ////////////////////////////////





    }
}