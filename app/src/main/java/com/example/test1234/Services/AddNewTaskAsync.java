package com.example.test1234.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.test1234.HttpClient;
import com.example.test1234.adapters.MyAdapter;
import com.example.test1234.models.Executor;
import com.example.test1234.models.Task;
import com.example.test1234.models.TaskPost;
import com.example.test1234.task;
import com.google.gson.Gson;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AddNewTaskAsync extends AsyncTask<TaskPost, String, Integer> {
    Context context;

    //public String response = "167";


    public  AddNewTaskAsync(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(TaskPost... task) {


//        Task task = new Task();
//        task.Title = "test";
//        task.Description = "nato";

        Gson gson = new Gson();
        String jsonString = gson.toJson(task[0]);
        Log.i("sendData", jsonString);

        Integer result = 0;

        try {
            Log.i("agee", jsonString);
            HttpClient httpClient = new HttpClient();
            String response = httpClient.httpPostBody("http://45.9.47.42:8737/api/Task/AddNewTask?Token=KMCG9553NOlQXOQf", new StringEntity(jsonString));

            result = Integer.parseInt( response.trim());

          //  Log.i("result", this.response);
        } catch (UnsupportedEncodingException ex) {
            ex.getStackTrace();
        }

        return result;
    }

//    @Override
//    protected void onPostExecute(final Integer taskArrayList) {
//        super.onPostExecute(taskArrayList);
//
//
//    }
}
