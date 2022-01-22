package john.bounty;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.util.Timer;
import java.util.TimerTask;



public class MainActivity  extends Activity {

    private static EditText email;
    private static EditText password;
    private Button myButton, myButton2;
    public static SharedPreferences sharedprefs;
    static CheckBox checkBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.liEmail);
        password = (EditText) findViewById(R.id.liPassword);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        myButton = (Button) findViewById(R.id.liLogin);
        myButton2 = (Button) findViewById(R.id.liRegister);
        sharedprefs = getSharedPreferences(String.valueOf(R.string.myPrefs), Context.MODE_PRIVATE);




            SharedPreferences.Editor editor = sharedprefs.edit();
            editor.clear();
            editor.apply(); }




    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static String getEmail() {
        return email.getText().toString(); }
    public static String getPassword()  {
        return password.getText().toString(); }



    public void loginPost(View view) {
        myButton.setEnabled(false);
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> myButton.setEnabled(true)); }}, 2000);


        final String link = "http://192.168.1.10:8000/bountyRegister/";
        /// builds a JSON object which includes usedata  (email, name , password, age)
        JSONObject userJsonParams = new JSONObject();
        try {
            userJsonParams.put("email",  getEmail());
        } catch (JSONException e) {
            e.printStackTrace(); }
        try {
            userJsonParams.put("password", getPassword()); }
        catch (JSONException e) {
            e.printStackTrace(); }
        try {
            userJsonParams.put("intent", "LOGIN"); }
        catch (JSONException e) {
            e.printStackTrace(); }



        try {
            ///parsing URL , JSON(userdata)
            new POSTActivity(MainActivity.this, null).execute(link, userJsonParams.toString());
        }
        catch (Exception e){
        }
        
    }





    public void RegisterActivity(View view) {
        myButton2.setEnabled(false);
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myButton2.setEnabled(true); }}); }}, 2000);

        startActivity(new Intent(this, RegisterActivity.class));

    }

}