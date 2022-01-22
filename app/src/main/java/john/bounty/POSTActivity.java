package john.bounty;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.entity.StringEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;



public class POSTActivity extends AsyncTask<String, Void ,String> {

    private Context context;
    private Activity activity;
    private ProgressBar progressBar;

    public static String getDeviceID(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return String.valueOf(Log.d(android_id, "TEST"));
    }


    public POSTActivity(Context context, String... args) {
        this.context = context;
        activity = (Activity) context;
        try {
            progressBar = activity.findViewById(R.id.pBar);
        }catch (Exception e){
        activity.runOnUiThread(new Runnable() {public void run() {
            Toast.makeText(activity, " " + e, Toast.LENGTH_SHORT).show(); }});
    }}

    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(ProgressBar.VISIBLE);

    }
    @Override
    protected String doInBackground(String... args) {
        BufferedReader reader = null;
        try {
            String link = args[0];
            String params = args[1];

            OutputStreamWriter wr;

            ///String params = URLEncoder.encode(user, "UTF-8");
            URL url = new URL(link);
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "application/json");
            conn.setDoOutput(true);

            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(params);
            wr.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, " " + e, Toast.LENGTH_SHORT).show();
                }
            });
            return "Connection Problem!";
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPostExecute(String result) {


        String[] tempArray = result.split("!");


        activity.runOnUiThread(new Runnable() {
            public void run() {
              Toast.makeText(activity,tempArray[0], Toast.LENGTH_SHORT).show();
             }
           });


        switch (tempArray[0]) {

            case "REGISTERED":
                context.startActivity(new Intent(context, MainActivity.class));
                break;
            case "MODIFIED":
                break;
            case "LOGGED":
                try {
                    SharedPreferences.Editor editor = MainActivity.sharedprefs.edit();
                    if (MainActivity.checkBox.isChecked()) {
                        editor.putString(String.valueOf(R.string.Email), MainActivity.getEmail());
                        editor.putString(String.valueOf(R.string.Password), MainActivity.getPassword());
                    }
                    editor.apply();
                } catch (Exception e) {
                    context.startActivity(new Intent(context, LogoutActivity.class));
                }break;}

                progressBar.setVisibility(ProgressBar.GONE);


    }
}