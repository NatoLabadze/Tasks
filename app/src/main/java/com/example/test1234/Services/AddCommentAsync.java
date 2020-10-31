package com.example.test1234.Services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import com.example.test1234.HttpClient;
import com.example.test1234.MainActivity;
import com.example.test1234.models.Executor;

public class AddCommentAsync extends AsyncTask<Executor, String, Integer> {
    Context context;
    public String id;
    public String text;

    public AddCommentAsync(Context context, String id, String text) {
        this.context = context;
        this.id = id;
        this.text = text;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Executor... executor) {

        Integer result = 0;

        HttpClient httpClient = new HttpClient();
        String responce = httpClient.httpPostBody("http://45.9.47.42:8737/api/Comment/AddComment?TaskId=" + id + "&Text=" + text + "&Token=KMCG9553NOlQXOQf", null);
        return result;
    }

    @Override
    protected void onPostExecute(final Integer taskArrayList) {
        super.onPostExecute(taskArrayList);
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
