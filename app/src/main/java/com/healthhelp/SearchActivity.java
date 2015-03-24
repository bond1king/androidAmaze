package com.healthhelp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * Created by bhargavsarvepalli on 21/03/15.
 */
public class SearchActivity extends Activity implements View.OnClickListener {
    String [] recommendations;
    AsyncHttpClient asyncHttpClient;
    private AutoCompleteTextViewExtended autoCompleteTextView;
    SharedPreferences pref;
    Button lucky;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        autoCompleteTextView = (AutoCompleteTextViewExtended) findViewById(R.id.autoCompleteTextView);
        asyncHttpClient = AsyncHttpClientUtils.getCommonAsyncHTTPClient();
        pref = this.getSharedPreferences("MyPref",0);
        lucky = (Button)findViewById(R.id.lucky);
        lucky.setOnClickListener(this);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {

                autoCompleteTextView.setText("");
                Intent intent = new Intent(SearchActivity.this, MedicineDetailsAndAlternatives.class);
                intent.putExtra("itemName", ((TextView)arg1).getText().toString() );
                startActivity(intent);

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
                String url=null;
                try {
                    url = "http://www.truemd.in/api/typeahead.json?id=" + URLEncoder.encode(id, "UTF-8") + "&key=yash6992&limit=5";
                }catch(Exception e){
                    e.printStackTrace();
                }
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


    @Override
    public void onClick(View v) {
        int userId = pref.getInt("id",-1);
        String email = pref.getString("email", null);
        String medicine = pref.getString("lastSearched",null);
        //String insertQuery = "insert into interaction (userId, medicine) values ('"+userId+"','"+ medicine +"')";
        String selectQuery = "SELECT user.Name, user.phoneNo, user.email FROM user, interaction WHERE interaction.userId = user.id AND user.email <> '" + email +"' ";
        if(medicine!=null && medicine.equals("null")) {
            selectQuery += " AND interaction.medicine = '" + medicine + "'";
        }
        switch (v.getId()){
            case R.id.lucky :{
                    new DBAsyncTask().execute(selectQuery, "select");
                break;
            }
            case R.id.search_help_button:{
             //      new com.healthhelp.DBAsyncTask().execute(insertQuery,"insert");
                break;
            }

        }

    }


    private class DBAsyncTask extends AsyncTask<String, Void, Boolean> {

        private Exception exception;
        private Connection conn;

        protected Boolean doInBackground(String... inputs) {
            try {
                System.out.println("EXECUTING BACKGROUND TASKS");
                String sql = inputs[0];
                System.out.println(sql);
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://sql3.freemysqlhosting.net/sql371289", "sql371289", "xE4!vH2!");
                Statement stmt = conn.createStatement();
                stmt.execute(inputs[0]);
                ResultSet rs = stmt.getResultSet();
                ArrayList names = new ArrayList();
                ArrayList phones = new ArrayList();
                ArrayList emails = new ArrayList();
                System.out.println("Hell0" );
                if (rs != null) {
                    while (rs.next()) {
                        names.add(rs.getString(1));
                        phones.add(rs.getString(2));
                        emails.add(rs.getString(3));
                    }
                    Intent i = new Intent(SearchActivity.this, ContactMessage.class);
                    i.putStringArrayListExtra("names", names);
                    i.putStringArrayListExtra("phones", phones);
                    i.putStringArrayListExtra("emails", emails);
                    startActivity(i);

                }
                conn.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                this.exception = e;
                return false;
            }
        }

        protected void onPostExecute() {

        }

    }
}
