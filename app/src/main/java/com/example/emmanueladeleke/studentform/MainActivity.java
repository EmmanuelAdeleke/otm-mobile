package com.example.emmanueladeleke.studentform;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvName;
    TextView tvEmail;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuestionTask questionTask = new QuestionTask();
        questionTask.execute();

        // Call init user method
        initUser();

        // Bind toolbar and set as ActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bind the textViews and set to user name and email
        tvName = (TextView) findViewById(R.id.tvFullName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvName.setText(user.getFirstName() + " " + user.getLastName());
        tvEmail.setText(user.getEmailAddress());

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

        Gson gson = new Gson();

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

    private class QuestionTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading questions");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Assign url and in
            String strJson;
            URL url;
            BufferedReader in = null;
            try {
                // Get query (username & password)
                url = new URL("http://emmanueladeleke.ddns.net:3000/otm/lecturer");
                in = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Get input from in and store in builder
            String inputLine;
            StringBuilder builder = new StringBuilder();
            try {
                while ((inputLine = in.readLine()) != null)
                    builder.append(inputLine);
            } catch (IOException e) {
                Log.d("IOException", "ioexception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.d("NullPointer", "Here is a nullpointer exception");

            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Remove all white spaces in strJson
            strJson = builder.toString().replaceAll("\\s+", "");

            // Check if JSON file is empty
            if (strJson.equals("[]")) {
                Log.d("AuthFail", strJson);
            } else {
                Log.d("AuthSuccess", strJson);
            }

            try {
                // Does not run on Marshmallow
                FileUtils.deleteQuietly(new File(Environment.getExternalStorageDirectory().toString() + "/lecturerQuestions.json"));
                FileUtils.writeStringToFile(new File(Environment.getExternalStorageDirectory().toString() + "/lecturerQuestions.json"), strJson, false);
                Log.d("success", strJson + "right here");

                // file.close();
            } catch (IOException e) {
                System.out.println("Cannot create file");
                Log.d("fail", "fail");
                Log.d("FilePath?", Environment.getExternalStorageDirectory().toString());
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
//
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
        }
    }
}
