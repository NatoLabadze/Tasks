package com.example.test1234.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.test1234.R;
import com.example.test1234.models.Task;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Task> {

    Context context;


    public MyAdapter(Context context, ArrayList<Task> employees) {
        super(context, 0, employees);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = layoutInflater.inflate(R.layout.row, null);
        Task currentTask =  (Task) getItem(position);


        TextView Title = view.findViewById(R.id.Title);
        TextView description = view.findViewById(R.id.descriprion);
      // TextView startDate = view.findViewById(R.id.startDate);
//        TextView endDate = view.findViewById(R.id.endDate);
//        TextView completeDate = view.findViewById(R.id.completeDate);
//        TextView comment = view.findViewById(R.id.comment);



        Title.setText(currentTask.Title);
        description.setText(currentTask.Description);
     //   startDate.setText( currentTask.StartDate.toString());
//        endDate.setText(currentTask.EndDate.toString());
//        completeDate.setText(currentTask.CompleteDate.toString());
//        comment.setText(currentTask.comments.Text);



        return view;

    }
}
