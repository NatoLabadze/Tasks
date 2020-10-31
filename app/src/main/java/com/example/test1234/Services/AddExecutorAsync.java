package com.example.test1234.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.test1234.HttpClient;
import com.example.test1234.models.Executor;
import com.example.test1234.models.Task;
import com.google.gson.Gson;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class AddExecutorAsync extends AsyncTask<Executor, String, Integer> {
    Context context;
    public int id;


    public AddExecutorAsync(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Executor... executor) {

//        Log.i("sendData1", executor[0].EmpId.toString());

        Integer taskId = executor[0].TaskId;
//        Log.i("sendData1", taskId);
        Integer empId = executor[0].EmpId;

        Integer result = 0;

        HttpClient httpClient = new HttpClient();
        String responce = httpClient.httpPostBody("http://45.9.47.42:8737/api/Executor/AddExecutor?EmpId=" + empId + "&TaskId=" + taskId + "&Token=KMCG9553NOlQXOQf", null);
//            result = Integer.parseInt( responce);

        return result;
    }

    @Override
    protected void onPostExecute(final Integer taskArrayList) {
        super.onPostExecute(taskArrayList);
    }
}
