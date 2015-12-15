package com.example.emmanueladeleke.studentform;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvTitle;
    Typeface titleFont;

    EditText etUsername, etPassword;
    Button bConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        tvTitle = (TextView) findViewById(R.id.tvTitle);

        titleFont = Typeface.createFromAsset(getAssets(), "fonts/titleFont.ttf");
        tvTitle.setTypeface(titleFont);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bConnect = (Button) findViewById(R.id.bConnect);



        bConnect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bConnect) {
            LoginTask task = new LoginTask();
            task.execute(etUsername.getText().toString(), etPassword.getText().toString());
            task.on

        }
    }

    private class LoginTask extends AsyncTask<String, String, String> {

        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        boolean result = false;
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Authenticating user...");
            dialog.show();

            boolean result = false;
        }

        @Override
        protected String doInBackground(String... params) {

            String username = params[0];
            String password = params[1];

            Log.d("Username", username);
            Log.d("Password", password);

            String strJson;
            URL url = null;
            BufferedReader in = null;
            try {
                url = new URL("http://emmanueladeleke.ddns.net:3000/otm/lecturer?query={\"username\":\"" + username + "\",\"password\":\"" + password + "\"}");
                in = new BufferedReader(new InputStreamReader(url.openStream()));
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            String inputLine;
            StringBuilder builder = new StringBuilder();
            try {
                while ((inputLine = in.readLine()) != null)
                    builder.append(inputLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {

                e.printStackTrace();
            }

            strJson = builder.toString().replaceAll("\\s+","");
            System.out.println();

            if (strJson.equals("[]")) {
                Log.d("hey", strJson);
            }
            else {
                //createFile(strJson);
                Log.d("hey", strJson);
            }

            //AuthUser authUser = new AuthUser(username, password);
           // authUser.authenticate();
            //Log.d("test!", authUser.authenticate() + "");
            //Log.d("username", username);
        return strJson;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
        }
    }
}
