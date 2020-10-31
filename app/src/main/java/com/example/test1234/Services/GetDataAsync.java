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
import com.example.test1234.models.Executor;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetDataAsync extends AsyncTask<String, String, ArrayList<Executor>> {
    protected boolean loading = false;
    ArrayList<String> employeeNames = new ArrayList<String>();
    public String text;
    Context context;
    MultiAutoCompleteTextView multi;

    public GetDataAsync(Context context, MultiAutoCompleteTextView multi) {
        this.context = context;
        this.multi = multi;
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
    protected ArrayList<Executor> doInBackground(String... strings) {
        HttpClient httpClient = new HttpClient();
        text = multi.getText().toString();

        if (text.contains(" ")) {
            String[] textsArray = text.split(" ");
//            String a = textsArray[0] + textsArray[1];
            text = textsArray[0] + textsArray[1];
        }
        Log.i("txt", text);
        String result = httpClient.httpGet("http://45.9.47.42:8737/api/Employee/GetEmployees?Name=" + text + "&Token=KMCG9553NOlQXOQf", null);
        ArrayList<Executor> employees = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObject = jsonArray.getJSONObject(i);
                Executor employee = new Executor();
                employee.FullName = jObject.getString("fullName");
                employee.EmpId = jObject.getInt("id");
                employees.add(employee);
            }
        } catch (JSONException ex) {
//            Toast.makeText(context, "წამოღება დასრულდა", Toast.LENGTH_SHORT).show();
        }
//        Log.i("ID", employees.get(0).Id.toString());
        return employees;
    }

    @Override
    protected void onPostExecute(final ArrayList<Executor> employees) {
        super.onPostExecute(employees);

        for (Executor executors : employees) {
            employeeNames.add(executors.FullName);
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, employeeNames);
        multi.setAdapter(adapter);
        multi.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}