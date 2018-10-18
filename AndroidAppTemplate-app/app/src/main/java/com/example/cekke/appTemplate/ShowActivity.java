package com.example.cekke.appTemplate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowActivity extends AppCompatActivity {
    private TextView showTitle, showDescr;
    private ImageView showValutaImage;

    private RequestQueue requestQueue;
    private static final String URL = "http://xxxxxxxxxxxxxxxxxxxxxxxxxxxx/appServer/Function.php";
    private StringRequest request;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        //ToolbarTitle invisible
        /*If you have an ActionBar, you can use getActionBar().hide();
        If you have a SupportActionBar, you can use getSupportActionBar().hide();*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("cod");

        showTitle = (TextView) findViewById(R.id.tv_show_name);
        showDescr = (TextView) findViewById(R.id.tv_show_descr);
        showValutaImage = (ImageView) findViewById(R.id.iv_show_image);

        init(id);
    }

    private void init(String code) {
        final String id = code.trim();

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
                        showTitle.setText(jsonObject.getString("name"));
                        showDescr.setText(jsonObject.getString("description"));
                        Picasso.get().load(jsonObject.getString("url")).into(showValutaImage);
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
                params.put("action", "getItem");
                params.put("id", id);
                return params;
            }
        };

        requestQueue.add(request);

    }
}
