package com.example.test1234.Services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.test1234.HttpClient;
import com.example.test1234.Popup_tasks;
import com.example.test1234.R;
import com.example.test1234.models.Employee;
import com.example.test1234.models.Executor;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetExecutorsAsync extends AsyncTask<String, String, ArrayList<Employee>> {
    protected boolean loading = false;
    public ArrayList<String> getEmployee = new ArrayList<String>();
    public String text;
    public String FullName = "";
    public String Description;
    public String Title;
    public String Comment;
    public String StarDate;
    public String EndDate;
    int Id;
    Context context;
    ListView listView;

    public GetExecutorsAsync(Context context, ListView listView, int Id, String Description, String Title, String Comment, String startDate, String endDate) {
        this.Id = Id;
        this.listView = listView;
        this.context = context;
        this.Title = Title;
        this.Description = Description;
        this.Comment = Comment;
        this.StarDate = startDate;
        this.EndDate = endDate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("იტვირთება");
//        progressDialog.show();
    }

    @Override
    protected ArrayList<Employee> doInBackground(String... strings) {
        HttpClient httpClient = new HttpClient();
        String result = httpClient.httpGet("http://45.9.47.42:8737/api/Executor/GetExecutors?TaskId=" + Id + "&Token=KMCG9553NOlQXOQf", null);
        ArrayList<Employee> employees = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObject = jsonArray.getJSONObject(i);

                Employee employee = new Employee();
                employee.Id = jObject.getInt("id");
                employee.FullName = jObject.getString("fullName");
//                if (jObject.getString("fullName")) {
//                    employee.FullName = jObject.getString("fullName");
//                } else {
//                    employee.FullName = "";
//                }
                employee.StructureId = jObject.getInt("structureId");

                employees.add(employee);
            }
        } catch (JSONException ex) {
//            Toast.makeText(context, "წამოღება დასრულდა", Toast.LENGTH_SHORT).show();
        }
        return employees;
    }

    @Override
    public void onPostExecute(final ArrayList<Employee> employees) {
        super.onPostExecute(employees);

//        for (Employee executors : employees
//        ) {
//            getEmployee.add(executors.FullName);
//        }
        if (employees.size() != 0) {
            for (Employee item : employees) {
                this.FullName += item.FullName + "\n";
            }
//            this.FullName = employees.get(0).FullName;
        } else {
            this.FullName = "შემსრულებელი ნეტუუუ";
        }



        Intent intent = new Intent(context, Popup_tasks.class);
        intent.putExtra("fullName", FullName);
        intent.putExtra("title", Title);
        intent.putExtra("description", Description);
        intent.putExtra("comment", Comment);
        intent.putExtra("id", Id);
        intent.putExtra("startDate", StarDate);
        intent.putExtra("endDate", EndDate);
        context.startActivity(intent);
    }
}