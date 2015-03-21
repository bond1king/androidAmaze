package com.healthhelp;

import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by bhargavsarvepalli on 21/03/15.
 */
public class DBAsyncTask extends AsyncTask<String, Void, Boolean> {

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
            boolean success = stmt.execute(sql);
            conn.close();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            this.exception = e;
            return false;
        }
    }

    protected void onPostExecute() {

    }
}
