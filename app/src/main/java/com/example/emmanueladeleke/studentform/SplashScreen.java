package com.example.emmanueladeleke.studentform;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView tvTitle;
    Typeface titleFont;
    int SPLASH_DISPLAY_LENGTH = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Custom font
        tvTitle = (TextView) findViewById(R.id.otmtitle);
        titleFont = Typeface.createFromAsset(getAssets(), "fonts/titleFont.ttf");
        tvTitle.setTypeface(titleFont);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
        setStatusBarColor(this);
    }

    public static void setStatusBarColor(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#041530"));
        }
    }
}
