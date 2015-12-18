package com.example.emmanueladeleke.studentform;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvName;
    TextView tvEmail;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initUser();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvName = (TextView) findViewById(R.id.tvFullName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);

        tvName.setText(user.getFirstName() + " " + user.getLastName());
        tvEmail.setText(user.getEmailAddress());

        JSONArray jsonArray = null;







        //tvText.setText(strJson);

    }

    public void initUser() {

        String strJson = "";
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/user.json");
        try {
            strJson = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(strJson);
            jsonObject = new JSONObject(jsonArray.get(0).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("jsonObject", jsonObject.toString());

//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();

        Gson gson = new Gson();

        user = gson.fromJson(jsonObject.toString(), User.class);

        user = gson.fromJson(jsonObject.toString(), User.class);

        //user = new gson.fromJson(jsonObject.toString(), User.class);


        Log.d("user", user.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
