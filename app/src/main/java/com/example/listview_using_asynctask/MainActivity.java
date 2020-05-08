package com.example.listview_using_asynctask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Details_pojo> details_pojoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        new fetchData().execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new fetchData().execute();
                Toast.makeText(MainActivity.this, "insideSwipeFresh", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class fetchData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            details_pojoList.clear();
            String result=null;

            try {
                URL url = new URL("https://reqres.in/api/users?page=2");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){

                    InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String temp;

                    while ((temp=reader.readLine())!=null){
                        stringBuilder.append(temp);                 //stringBuilder mein temp daaldo
                    }
                    result = stringBuilder.toString();
                }
                else {
                    result = "error!";
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String string) {
            swipeRefreshLayout.setRefreshing(false);

            Log.d("insidePostExecute","inside.....");
            try {
                JSONObject jsonObject = new JSONObject(string);                 // converted string into jsonObject
                JSONArray jsonArray = jsonObject.getJSONArray("data");      // jsonobjects are in jsonArray named data

                for (int i=0;i<jsonArray.length();i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String email = jsonObject1.getString("email");
                    String name = jsonObject1.getString("first_name");
                    String lastname = jsonObject1.getString("last_name");

                    Toast.makeText(MainActivity.this, ""+name+ " " + email, Toast.LENGTH_SHORT).show();
                    
                    Details_pojo details_pojo = new Details_pojo();

                    details_pojo.setEmail(jsonObject1.getString("email"));
                    details_pojo.setName(jsonObject1.getString("first_name"));

                    details_pojoList.add(details_pojo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListAdapter adapter = new ListAdapter(MainActivity.this,details_pojoList);
            listView.setAdapter(adapter);
        }
    }

}