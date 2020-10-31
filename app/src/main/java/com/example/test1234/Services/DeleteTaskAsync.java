package com.example.test1234.Services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.test1234.Context_menu;
import com.example.test1234.HttpClient;
import com.example.test1234.MainActivity;

public class DeleteTaskAsync extends AsyncTask<Integer, String, Integer> {
    Context context;
    public String id;


    public DeleteTaskAsync(Context context, String id) {
        this.context = context;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Integer... integers) {

        Integer result = 0;

        HttpClient httpClient = new HttpClient();
        String response = httpClient.httpDelete("http://45.9.47.42:8737/api/Task/DeleteTask?Id=" + id + "&Token=KMCG9553NOlQXOQf");
        return result;
    }

    @Override
    protected void onPostExecute(final Integer taskArrayList) {
        super.onPostExecute(taskArrayList);

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
