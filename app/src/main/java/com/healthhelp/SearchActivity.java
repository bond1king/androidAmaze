package com.healthhelp;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


/**
 * Created by bhargavsarvepalli on 21/03/15.
 */
public class SearchActivity extends Activity {
    String [] recommendations;
    AsyncHttpClient asyncHttpClient;
    private AutoCompleteTextViewExtended autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println(" AutoComplete here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        autoCompleteTextView = (AutoCompleteTextViewExtended) findViewById(R.id.autoCompleteTextView);
        asyncHttpClient = AsyncHttpClientUtils.getCommonAsyncHTTPClient();

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                //TODO REMOVE AFTER HANDLING THE INPUT
                autoCompleteTextView.setText("");
                //TODO Nadeem

            } });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    if (s.toString().contains(" ")) {
                        s.toString().substring(0, count - 2);
                        Toast.makeText(getApplicationContext(), "Space is not allowed", Toast.LENGTH_LONG).show();
                    }
                    getRecommendations(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });




    }


            public void getRecommendations(String id) {
                String url = "http://www.truemd.in/api/typeahead.json?id=" + id + "&key=yash6992&limit=5";
                asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        super.onSuccess(response);
                        response = response.trim();
                        System.out.println(response);
                        if (response != null && response.length() != 0) {
                            SearchActivity.this.recommendations = response.substring(1, response.length() - 1).split(",");
                        }
                        ArrayAdapter adapter = new ArrayAdapter
                                (SearchActivity.this, android.R.layout.simple_list_item_1, SearchActivity.this.recommendations);
                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView.showDropDown();
                    }

                    @Override
                    public void onFailure(Throwable throwable, String s) {
                        System.out.println("faileds");
                        super.onFailure(throwable, s);
                    }
                });

            }


        }
