package com.healthhelp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bhargavsarvepalli on 22/03/15.
 */
public class ContactMessage extends Activity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       ArrayList<String> names =  getIntent().getStringArrayListExtra("names");
       ArrayList<String> phones =  getIntent().getStringArrayListExtra("phones");
       ArrayList<String> emails =  getIntent().getStringArrayListExtra("emails");
        setContentView(R.layout.contactmessage);
        LinearLayout ll = (LinearLayout)findViewById(R.id.contactLayout);
        for(int i=0; i< names.size(); i++){
           TextView tv = new TextView(this);
           tv.setText(names.get(i)+" "+ phones.get(i));
           tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
           tv.setTextColor(Color.BLUE);
           tv.setOnClickListener(this);
           ll.addView(tv);
       }

    }

    @Override
    public void onClick(View v) {
        /** Creating an intent to initiate view action */
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse("sms:" + ((TextView)v).getText().toString().split(" ")[1]));
        startActivity(smsIntent);
    }
}
