package com.example.cekke.appTemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        /*ToolbarTitle invisible
        If you have an ActionBar, you can use getActionBar().hide();
        If you have a SupportActionBar, you can use getSupportActionBar().hide();*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //this is for show the 'comeback-button'
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
