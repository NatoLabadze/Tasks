package com.example.test1234.Services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.test1234.HttpClient;
import com.example.test1234.Popup_tasks;
import com.example.test1234.adapters.MyAdapter;
import com.example.test1234.models.Comment;
import com.example.test1234.models.Employee;
import com.example.test1234.models.Executor;
import com.example.test1234.models.Paging;
import com.example.test1234.models.Task;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GetCommentsAsync extends AsyncTask<String, String, ArrayList<Comment>> {
    public String Description;
    public String Title;
    public String Comment = "";
    public String StartDate;
    public String EndDate;
    ArrayList<String> getEmployee = new ArrayList<String>();
    Context context;
    ListView listView;
    int Id;

    public GetCommentsAsync(Context context, ListView listView, int Id, String Description, String Title, String startDate, String endDate) {
        this.Id = Id;
        this.listView = listView;
        this.context = context;
        this.Title = Title;
        this.Description = Description;
        this.StartDate = startDate;
        this.EndDate = endDate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Comment> doInBackground(String... strings) {

        HttpClient httpClient = new HttpClient();
        String result = httpClient.httpGet("http://45.9.47.42:8737/api/Comment/GetComments?TaskId=" + Id + "&Token=KMCG9553NOlQXOQf", null); //aq id-is ro wer ireva ratomgac da eg naze axla 123 ro weria
        ArrayList<Comment> tasks = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObject = jsonArray.getJSONObject(i);
                Comment comment = new Comment();

                DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                comment.id = jObject.getInt("id");
                comment.TaskId = jObject.getInt("taskId");
                comment.Text = jObject.getString("text");
                comment.ActiveDate = format.parse(jObject.getString("activeDate"));
                tasks.add(comment);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    protected void onPostExecute(final ArrayList<Comment> taskArrayList) {
        super.onPostExecute(taskArrayList);
        if (taskArrayList.size() != 0) {
            for (Comment item : taskArrayList) {
                this.Comment += item.Text + "\n";
            }
        } else {
            this.Comment = "კომენტარები არაა მიბმული";
        }

        new GetExecutorsAsync(context, listView, Id, Description, Title, Comment, StartDate, EndDate).execute();
//
        for (Comment executors : taskArrayList
        ) {
            getEmployee.add(executors.Text);
        }
    }
}

