package com.healthhelp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by bhargavsarvepalli on 21/03/15.
 */
public class MedicineDetailsAndAlternatives extends Activity {
    AsyncHttpClient asyncHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        String queryItem = getIntent().getStringExtra("itemName");
        asyncHttpClient = AsyncHttpClientUtils.getCommonAsyncHTTPClient();
        getDetails(queryItem);
        getAlternatives(queryItem);



    }

    private void getAlternatives(String item){
        String url=null;
        try {
            url = "http://www.truemd.in/api/medicine_alternatives?id=" + URLEncoder.encode(item.substring(1,item.length()-1),"UTF-8") + "&key=af494a80bc65dd77f88ac4efa99742&limit=10";
        }catch(Exception e){
            e.printStackTrace();
        }
            asyncHttpClient.get(url, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                findViewById(R.id.alternativesProgressBar).setVisibility(View.GONE);
                ArrayList<Medicine> alternatives = TrueMDAPI.getMedicineAlternatives(response);

                if(response!=null && alternatives.size()>0) {
                    ((TextView) findViewById(R.id.alt1_name)).setText(alternatives.get(0).getBrand());
                    ((TextView) findViewById(R.id.alt1_price)).setText(alternatives.get(0).getUnitPrice());
                    if (alternatives.size() > 1) {
                        ((TextView) findViewById(R.id.alt2_name)).setText(alternatives.get(1).getBrand());
                        ((TextView) findViewById(R.id.alt2_price)).setText(alternatives.get(1).getUnitPrice());

                        if (alternatives.size() > 2) {
                            ((TextView) findViewById(R.id.alt3_name)).setText(alternatives.get(2).getBrand());
                            ((TextView) findViewById(R.id.alt3_price)).setText(alternatives.get(2).getUnitPrice());

                            if (alternatives.size() > 3) {
                                ((TextView) findViewById(R.id.alt4_name)).setText(alternatives.get(3).getBrand());
                                ((TextView) findViewById(R.id.alt4_price)).setText(alternatives.get(3).getUnitPrice());
                            }
                        }
                    }
                }
                   findViewById(R.id.alternatives).setVisibility(View.VISIBLE);

             }

        });
    }

    private void getDetails(String item){

        String url = null;
        System.out.println(item);
        try {
            url = "http://www.truemd.in/api/medicine_details?id=" + URLEncoder.encode(item.substring(1,item.length()-1),"UTF-8") + "&key=af494a80bc65dd77f88ac4efa99742&limit=100";
            System.out.println(url);
        }catch(Exception e){
            e.printStackTrace();
        }
        asyncHttpClient.get(url, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                if(response!=null) {
                   Medicine med = TrueMDAPI.getMedicineDetails(response);
                    ((TextView)findViewById(R.id.brand)).setText(med.getBrand());
                    ((TextView)findViewById(R.id.category)).setText(med.getCategory());
                    ((TextView)findViewById(R.id.unitPrice)).setText(med.getUnitPrice());
                    ((TextView)findViewById(R.id.genericId)).setText(med.getGenericId());
                    findViewById(R.id.details).setVisibility(View.VISIBLE);
                    findViewById(R.id.DetailsProgressBar).setVisibility(View.GONE);
                }
            }
        });
    }

}
