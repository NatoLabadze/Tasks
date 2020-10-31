package com.example.test1234.Services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.test1234.Context_menu;
import com.example.test1234.HttpClient;
import com.example.test1234.adapters.MyAdapter;
import com.example.test1234.models.Employee;
import com.example.test1234.models.Paging;
import com.example.test1234.models.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GetTaskAsync extends AsyncTask<String, String, ArrayList<Task>> {
    public ArrayList<Task> tasks = new ArrayList<Task>();
    public ArrayList<Employee> executors = new ArrayList<Employee>();
    public String fullName = "asd";
    private final MyAdapter myAdapter;
    private int selectedTaskId;
    Context context;
    ListView listView;

    public  GetTaskAsync(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;

        myAdapter = new MyAdapter(context, new ArrayList<Task>());
        listView.setAdapter(myAdapter);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Task> doInBackground(String... strings) {

        HttpClient httpClient = new HttpClient();
        String result = httpClient.httpGet("http://45.9.47.42:8737/api/Task/GetTasks?Id=-1&EmpId=-1&ManagerId=-1&StartDate=2019-01-05T13%3A03%3A11.607Z&EndDate=2021-12-31T13%3A03%3A11.607Z&Status=-1&CurrentPage=1&ItemsPerPage=10000&Token=KMCG9553NOlQXOQf", null);
        ArrayList<Task> tasks = new ArrayList<>();

        Paging paging = null;

        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObject = jsonArray.getJSONObject(i);
                Task task = new Task();


                DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                task.Id = jObject.getInt("id");
                task.ManagerId = jObject.getInt("managerId");
                task.ExName = jObject.getString("exName");
                task.StFullName = jObject.getString("stFullName");
                task.Title = jObject.getString("title");
                task.Description = jObject.getString("description");
                task.StartDate = jObject.getString("startDate").split("T")[0];
                task.EndDate = jObject.getString("endDate").split("T")[0];
                String complateDate = jObject.getString("completeDate");
                if (complateDate != "null") {
                    task.CompleteDate = format.parse(complateDate);
                }

                String isDelete = jObject.getString("isDeleted");

                if (isDelete != "null") {
                    task.IsDeleted = jObject.getBoolean("isDeleted");
                }
                task.StatusId = jObject.getInt("statusId");
                task.NumberOfCom = jObject.getInt("numberOfCom");

                JSONObject pagingObject = jObject.getJSONObject("paging");

                if (paging == null) {
                    paging = new Paging();

                    paging.ItemsPerPage = pagingObject.getInt("itemsPerPage");
                    paging.CurrentPage = pagingObject.getInt("currentPage");
                    paging.TotalItems = pagingObject.getInt("totalItems");
                    paging.TotalPages = pagingObject.getInt("totalPages");
                }

                tasks.add(task);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        getExecutors(selectedTaskId);
        return tasks;
    }

    @Override
    protected void onPostExecute(final ArrayList<Task> taskArrayList) {
        super.onPostExecute(taskArrayList);


        for (Task cInfo : taskArrayList) {
            myAdapter.add(cInfo);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(context, Context_menu.class);
                    selectedTaskId = taskArrayList.get(position).Id;
//                  getExecutors(selectedTaskId);
                    String Title = taskArrayList.get(position).Title;
                    String Description = taskArrayList.get(position).Description;
                    String startDate = taskArrayList.get(position).StartDate.toString();
                    String endDate = taskArrayList.get(position).EndDate.toString();
                    Log.i("id", Integer.toString(selectedTaskId));

                    intent.putExtra("title", Title);
                    intent.putExtra("description", Description);
                    intent.putExtra("id", Integer.toString(selectedTaskId));
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("endDate", endDate);

                    context.startActivity(intent);
                }

            });

        }
    }
}
