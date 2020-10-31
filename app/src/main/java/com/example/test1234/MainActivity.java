package com.example.test1234;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.test1234.Services.AddNewTaskAsync;
import com.example.test1234.Services.GetTaskAsync;
import com.example.test1234.models.Task;

public class MainActivity extends Activity {

    ListView listView2;
    Button btn;
    Button refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView2 = findViewById(R.id.listView);
        btn = findViewById(R.id.add);
        refreshBtn = findViewById(R.id.refresh);

//  Button Click


//        Task task = new Task();
//        task.Title = "test";
//        task.Description = "nato";
//
//        new AddNewTaskAsync(this).execute(task);

        new GetTaskAsync(this, listView2).execute();

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, task.class);
                MainActivity.this.startActivity(intent);
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new GetTaskAsync(MainActivity.this, listView2).execute();
            }
        });
    }
}