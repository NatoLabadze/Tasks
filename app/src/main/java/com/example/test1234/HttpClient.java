package com.example.test1234;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;

public class HttpClient {

    public String httpGet(final String url, String token) {
        InputStream inputStream = null;
        // Declare a string builder to help with the parsing.
        StringBuilder stringBuilder = new StringBuilder();


        // Construct the client and the HTTP request.
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        if (token != null)
            httpGet.addHeader("Authorization", token); // add the authorization header to the request

        // Making HTTP request
        try {
            // Execute the POST request and store the response locally.
            HttpResponse httpResponse = httpClient.execute(httpGet);
            // Extract data from the response.
            HttpEntity httpEntity = httpResponse.getEntity();
            // Open an inputStream with the data content.
            inputStream = httpEntity.getContent();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Create a BufferedReader to parse through the inputStream.
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);

            // Declare a string to store the JSON object data in string form.
            String line = null;
            // Build the string until null.
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            // Close the input stream.
            inputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public JSONObject httpPostForm(String url, String token, List<NameValuePair> params) {
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);

        if (token != null)
            httpPost.addHeader("Authorization", token); // add the authorization header to the request
//        httpPost.setHeader("Content-Type", "application/json");


        // მოთხოვნის გაკეტება
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // პასუხის დამუშავება
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // string -ის პარსვა json -ად
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return jsonObject;
    }


    public String httpPostBody(final String url, StringEntity entity) {
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json; charset=UTF-8");


        // მოთხოვნის გაკეტება
        try {
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // პასუხის დამუშავება
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public String httpDelete(final String url) {
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpDelete httpPost = new HttpDelete(url);


        // მოთხოვნის გაკეტება
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // პასუხის დამუშავება
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return stringBuilder.toString();
    }

}


