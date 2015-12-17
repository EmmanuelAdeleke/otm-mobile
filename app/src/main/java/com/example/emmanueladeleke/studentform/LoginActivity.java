package com.example.emmanueladeleke.studentform;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileWriter;
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

        tvTitle = (TextView) findViewById(R.id.tvTitle);

        titleFont = Typeface.createFromAsset(getAssets(), "fonts/titleFont.ttf");
        tvTitle.setTypeface(titleFont);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bConnect = (Button) findViewById(R.id.bConnect);

        setStatusBarColor(this);

        // Window.setStatusBarColor(R.color.login_grey);
        bConnect.setOnClickListener(this);
    }

    public static void setStatusBarColor(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#616161"));
        }
    }

    @Override
    public void onClick(View v) {

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (v.getId() == R.id.bConnect) {

            if (username.equals("".trim()) || password.toString().equals("".trim())) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Do not leave fields empty.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .show();
            } else {

                LoginTask task = new LoginTask();
                task.execute(etUsername.getText().toString(), etPassword.getText().toString());
            }

        }
    }

    private class LoginTask extends AsyncTask<String, String, String> {

        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        boolean result = false;
        String strJson;

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

            strJson = null;
            URL url = null;
            BufferedReader in = null;
            try {
                url = new URL("http://emmanueladeleke.ddns.net:3000/otm/lecturer?query={\"username\":\"" + username + "\",\"password\":\"" + password + "\"}");
                in = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }

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
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {

                e.printStackTrace();
            }

            strJson = builder.toString().replaceAll("\\s+", "");
            System.out.println();

            if (strJson.equals("[]")) {
                Log.d("AuthFail", strJson);
            } else {
                //createFile(strJson);
                Log.d("AuthSuccess", strJson);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //AuthUser authUser = new AuthUser(username, password);
            // authUser.authenticate();
            //Log.d("test!", authUser.authenticate() + "");
            //Log.d("username", username);
            return strJson;
        }

        @Override
        protected void onPostExecute(String str) {
            dialog.dismiss();

            if (strJson.equals("[]")) {
                UserDialog.showMessageToUser(LoginActivity.this, "Wrong login details. Try again");
            } else {
                try {
                    // Does not run on Marshmallow
                    FileWriter file = new FileWriter(Environment.getExternalStorageDirectory() + "/user.json", true);
                    file.write(strJson);
                    Log.d("success", "success");
                    file.close();
                } catch (IOException e) {
                    System.out.println("Cannot create file");
                    Log.d("fail", "fail");
                    Log.d("FilePath?", getFilesDir().toString());
                    e.printStackTrace();
                }
            }
            finish();

        }
    }
}
