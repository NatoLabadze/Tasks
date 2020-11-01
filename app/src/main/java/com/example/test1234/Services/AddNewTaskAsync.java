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
    public ArrayList<String> selectedIds;
    public String taskId;


    public  AddNewTaskAsync(Context context, ArrayList<String> selectedIds) {
        this.context = context;
        this.selectedIds = selectedIds;
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
            this.taskId = response;

          //  Log.i("result", this.response);
        } catch (UnsupportedEncodingException ex) {
            ex.getStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(final Integer taskArrayList) {
        super.onPostExecute(taskArrayList);
//        Executor executor = new Executor();
//        executor.TaskId = Integer.getInteger(taskId);
//        executor.EmpId = Integer.getInteger(selectedIds.get(0));
////        Log.i("exaa2", executor.EmpId.toString());
////        Log.i("exaa1", executor.TaskId.toString());
//
//        new AddExecutorAsync(context).execute(executor);
//
////        for (String id : selectedIds) {
//////            Executor executor = new Executor();
////            executor.TaskId = Integer.getInteger(taskId);
//////            executor.EmpId = Integer.getInteger(id);
////
////            Log.i("exeeee", id);
////
//////            new AddExecutorAsync(context).execute(executor);
////        }

    }
}
