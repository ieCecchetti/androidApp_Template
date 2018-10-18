package com.example.cekke.appTemplate;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ToolbarTitle invisible
        /*If you have an ActionBar, you can use getActionBar().hide();
        If you have a SupportActionBar, you can use getSupportActionBar().hide();*/
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().hide();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_Credits:
                Intent gotoMain = new Intent(MainActivity.this, CreditsActivity.class);
                startActivity(gotoMain);
                return true;
            case R.id.action_Logout:
                buildAlertMessageOnExit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void buildAlertMessageOnExit() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Stai per uscire dall'applicazione \n" +
                "Se continui i dati relativi al tuo account saranno persi e dovrai rieffettuare il Login\n" +
                "Vuoi uscire comunque?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        Intent startApp = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(startApp);
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
        Button buttonPositive = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        Button buttonNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

    }
}
