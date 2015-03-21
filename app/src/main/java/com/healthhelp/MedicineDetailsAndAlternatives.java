package com.healthhelp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
        String url = "http://www.truemd.in/api/medicine_alternatives?id="+item+"&key=af494a80bc65dd77f88ac4efa99742&limit=10";
        asyncHttpClient.get(url, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                TextView tview = (TextView)findViewById(R.id.alternatives);
                tview.setText(response);
            }
        });
    }

    private void getDetails(String item){
        String url = "http://www.truemd.in/api/medicine_details?id="+item+"&key=af494a80bc65dd77f88ac4efa99742&limit=100";
        asyncHttpClient.get(url, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                TextView tview = (TextView)findViewById(R.id.details);
                tview.setText(response);
            }
        });
    }

}
