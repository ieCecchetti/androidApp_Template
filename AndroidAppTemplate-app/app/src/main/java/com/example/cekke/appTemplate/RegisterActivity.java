package com.example.cekke.appTemplate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Spinner spCountry;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;
    private RequestQueue requestQueue;
    private static final String URL = "http://xxxxxxxxxxxxxxxxxxxxxxxxxxxx/appServer/Function.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*ToolbarTitle invisible
        If you have an ActionBar, you can use getActionBar().hide();
        If you have a SupportActionBar, you can use getSupportActionBar().hide();*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //this is for show the 'comeback-button'
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmail = (EditText) findViewById(R.id.et_email_register);
        editTextEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editTextEmail.setText("");
            }
        });
        editTextUsername = (EditText) findViewById(R.id.et_username_register);
        editTextUsername.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editTextUsername.setText("");
            }
        });
        editTextPassword = (EditText) findViewById(R.id.et_password_register);
        editTextPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editTextPassword.setText("");
            }
        });

        progressDialog = new ProgressDialog(this);

        requestQueue = Volley.newRequestQueue(this);

        buttonRegister = (Button) findViewById(R.id.btn_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        textViewLogin = (TextView) findViewById(R.id.tv_login);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(gotoLogin);
                finish();
            }
        });
    }

    private void registerUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("verifying credential...");
        progressDialog.show();

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();

                    Log.i("Response",response);
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean("success")){
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent gotoLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(gotoLogin);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Error: " +jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                        error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "register");
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        requestQueue.add(request);


    }
}
