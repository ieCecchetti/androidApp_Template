package com.example.cekke.appTemplate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextView register;
    private EditText et_MailorUser, et_password;
    private Button sign_in;
    private RequestQueue requestQueue;
    private static final String URL = "http://xxxxxxxxxxxxxxxxxxxxxxxxxxxx/appServer/Function.php";
    private StringRequest request;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*ToolbarTitle invisible
        If you have an ActionBar, you can use getActionBar().hide();
        If you have a SupportActionBar, you can use getSupportActionBar().hide();*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();

        requestQueue = Volley.newRequestQueue(this);

        et_MailorUser = (EditText) findViewById(R.id.et_email);
        et_MailorUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                et_MailorUser.setText("");
            }
        });
        et_password = (EditText) findViewById(R.id.et_password);
        et_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                et_password.setText("");
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        sign_in = (Button) findViewById(R.id.btn_Log);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin(et_MailorUser.getText().toString(), et_password.getText().toString());
            }
        });
        register = (TextView) findViewById(R.id.tv_registration);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToRegister(view);
            }
        });

        boolean isLog = SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn();
        String usr = SharedPrefManager.getInstance(getApplicationContext()).getUsername();
        String psw = SharedPrefManager.getInstance(getApplicationContext()).getPassword();
        Log.i("check", "user: " + usr + " psw: " + psw);

        if (isLog) {
            userLogin(usr, psw);
        }
    }

    public void goToRegister(View view) {
        Intent gotoRegistration = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(gotoRegistration);
    }

    private void userLogin(String user, String psw) {
        final String username = user.trim();
        final String password = psw.trim();

        progressDialog.setMessage("verifying credential...");
        progressDialog.show();

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    Log.i("Response", response);
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response",response.toString());
                    if (jsonObject.getBoolean("success")) {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        SharedPrefManager.getInstance(getApplicationContext())
                                .userLogin(
                                        jsonObject.getString("uid"),
                                        jsonObject.getString("username"),
                                        jsonObject.getString("email"),
                                        password
                                );
                        SharedPrefManager.getInstance(getApplicationContext())
                                .userLoginSecondaryInfo(
                                        jsonObject.getString("date"),
                                        jsonObject.getString("permission")
                                );

                        Intent gotoMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(gotoMain);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(
                        getApplicationContext(),
                        "Errore nel tentativo di connessione, forse sei offline?",
                        Toast.LENGTH_LONG
                ).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "login");
                params.put("user", username);
                params.put("password", password);
                return params;
            }
        };

        requestQueue.add(request);

    }
}
