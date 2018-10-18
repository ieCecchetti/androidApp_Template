package com.example.cekke.appTemplate;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IntroActivity extends AppCompatActivity {

    //intro animation
    private static int SHOW_TIME=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        /*ToolbarTitle invisible
        If you have an ActionBar, you can use getActionBar().hide();
        If you have a SupportActionBar, you can use getSupportActionBar().hide();*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent gotoLogin = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(gotoLogin);
                finish();
            }
        },SHOW_TIME);
    }
}
