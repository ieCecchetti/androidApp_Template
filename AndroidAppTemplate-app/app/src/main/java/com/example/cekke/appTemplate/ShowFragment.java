package com.example.cekke.appTemplate;

/**
 * Created by cekke on 17/05/2017.
 */

import android.app.ProgressDialog;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cekke.appTemplate.adapters.ItemAdapter;
import com.example.cekke.appTemplate.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowFragment extends Fragment {
    private RecyclerView mCryptRecycleView;
    private ItemAdapter mAdapter;
    private ArrayList<Item> mCryptoCollectionTemp, mCryptCollectionPrint;

    private RequestQueue requestQueue;
    private static final String URL = "http://xxxxxxxxxxxxxxxxxxxxxxxx/appServer/Function.php";
    private StringRequest request;
    private ProgressDialog progressDialog;
    private static boolean reload;

    //max# of locali plotted at time
    private int actualMax=10;
    //actual page
    private int actualPage=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_show, container, false);


        requestQueue = Volley.newRequestQueue(this.getContext());

        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Please wait...");

        init(rootView);
        printValute();

        return rootView;
    }

    private void init(View root) {
        mCryptRecycleView = (RecyclerView) root.findViewById(R.id.valute_recycler);
        mCryptRecycleView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        mCryptRecycleView.setHasFixedSize(true);
        mCryptoCollectionTemp = new ArrayList<>();
        mCryptCollectionPrint = new ArrayList<>();
        mAdapter = new ItemAdapter(mCryptCollectionPrint, this.getActivity());
        mCryptRecycleView.setAdapter(mAdapter);

    }

    public void printValute()
    {
        final String ratio = "15".trim();
        progressDialog.setMessage("loading items...");
        progressDialog.show();

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    //Log.i("Response",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray valuteArray = jsonObject.getJSONArray("items");

                    //list = new ArrayList<>();

                    for (int i = 0; i < valuteArray.length(); i++) {

                        int codL;
                        String name;
                        String value;
                        String link;


                        JSONObject jValuta = (JSONObject) valuteArray.get(i);
                        // Log.v("Response", jRestaurant.toString());
                        codL = jValuta.getInt("cid");
                        name =  Html.fromHtml(jValuta.getString("name")).toString();
                        value =  Html.fromHtml(jValuta.getString("tendency")).toString();
                        link = jValuta.getString("url");

                        Log.v("BRAD_", i + ": "+codL+" "+name+" "+value+" "+link);

                        Item valuta = new Item();
                        valuta.setId(codL);
                        valuta.setName(name);
                        valuta.setValue(value);
                        valuta.setUrlImage(link);

                        mCryptoCollectionTemp.add(valuta);
                    }
                    PrintValute(0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(
                        getContext(),
                        error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "getItemList");
                return params;
            }
        };

        requestQueue.add(request);
    }

    private void PrintValute(int pag)
    {
        mCryptCollectionPrint.clear();
        if ( mCryptoCollectionTemp.size()<actualMax)
        {
            if (pag>0)
            {
                Toast.makeText(this.getContext(), "Non ci sono altri locali nella in questa categoria", Toast.LENGTH_LONG).show();
                actualPage--;
            }
            //if # is less then actualMax then print only those
            for (int i=0; i< mCryptoCollectionTemp.size(); i++)
            {
                mCryptCollectionPrint.add(mCryptoCollectionTemp.get(i));
            }

        }else{
            //instead if #is more then the actualMax keep looking to the page #
            //so, start from 0 to page+1*actualMax (2*10)
            //if pag >then 3,then message "impossible to search more then 30 locali"
            if (pag>3)
            {
                Toast.makeText(this.getContext(), "La ciccionacggine ha un massimo zio, Impossibile caricare piu' di 30 locali", Toast.LENGTH_LONG).show();
            }else
            {
                Log.i("check","if: "+mCryptoCollectionTemp.size()+" < "+(pag*actualMax));
                if(mCryptoCollectionTemp.size() < (pag*actualMax))
                {
                    Toast.makeText(this.getContext(), "Non ci sono altri locali nella in questa categoria", Toast.LENGTH_LONG).show();
                    actualPage--;
                    int startPos=0;
                    int until=(pag+1)*actualMax;
                    if (until>mCryptoCollectionTemp.size())
                    {
                        until=mCryptoCollectionTemp.size();
                    }
                    Log.i("check","start: "+startPos+" end: "+until);
                    for (int i=startPos; i< until; i++)
                    {
                        mCryptCollectionPrint.add(mCryptoCollectionTemp.get(i));
                    }
                }else
                {
                    int startPos=0;
                    int until=(pag+1)*actualMax;
                    if (until>mCryptoCollectionTemp.size())
                    {
                        until=mCryptoCollectionTemp.size();
                    }
                    Log.i("check","start: "+startPos+" end: "+until);
                    for (int i=startPos; i< until; i++)
                    {
                        mCryptCollectionPrint.add(mCryptoCollectionTemp.get(i));
                    }
                }

            }
        }
        //the fake one (last restaurant item)
        Item valuta = new Item();
        mCryptCollectionPrint.add(valuta);

        mAdapter.notifyDataSetChanged();
    }

    public static void setReload(boolean reload_value) {
        reload = reload_value;
    }


}
