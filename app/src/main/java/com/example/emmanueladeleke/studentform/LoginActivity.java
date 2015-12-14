package com.example.emmanueladeleke.studentform;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        bConnect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bConnect) {
//            LoginTask task = new LoginTask();
//            task.execute();

        }
    }
}
