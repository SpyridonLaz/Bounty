package john.bounty;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends Activity {

    private EditText email, password, repassword, name, age;
    private RelativeLayout subLayout1, subLayout2;
    private Button register;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        subLayout1 = findViewById(R.id.sublayout1);
        subLayout2 = findViewById(R.id.sublayout2);
        subLayout2.setVisibility(View.GONE);
        email = findViewById(R.id.REGemail);
        password = findViewById(R.id.REGpassword);
        repassword = findViewById(R.id.REGrepassword);
        name = findViewById(R.id.REGname);
        age = findViewById(R.id.REGage);
        register = findViewById(R.id.REGregister);


        register.setOnClickListener(v -> {
            register.setEnabled(false);
            Timer buttonTimer = new Timer();
            buttonTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> register.setEnabled(true)); }
                        }, 2000);

             final String link = "http://192.168.1.10:8000/bountyRegister/";
            /// builds a JSON object which includes usedata  (email, name , password, age)
            JSONObject  userJsonParams = new JSONObject();
            try {
                userJsonParams.put("email",  getEmail());
            } catch (JSONException e) {
                e.printStackTrace(); }
            try {
                userJsonParams.put("password", getPassword()); }
            catch (JSONException e) {
                e.printStackTrace(); }
            try {
                userJsonParams.put("name", getName()); }
            catch (JSONException e) {
                e.printStackTrace(); }
            try {
                userJsonParams.put("age", getAge()); }
            catch (JSONException e) {
                e.printStackTrace(); }
            try {
                userJsonParams.put("intent", "LOGIN"); }
            catch (JSONException e) {
                e.printStackTrace(); }



            try {

                ///parsing URL , JSON(userdata)
                new POSTActivity(RegisterActivity.this, null).execute(link, userJsonParams.toString());
            }
            catch (Exception e){
                RegisterActivity.this.runOnUiThread(new Runnable() {public void run() {
                    Toast.makeText(RegisterActivity.this, " " + e, Toast.LENGTH_SHORT).show(); }});
            }
        });
    }



    /// Hide keyboard ///
    public void hideKeyboard(View view) {
        if (!(view instanceof EditText)) {
            try {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); }
            catch (Exception e) {
                // TODO: handle exception}}
               }
        } }



    /// returns current TextView values with usedata.

    private String getEmail() {
        return String.valueOf(email.getText()); }
    private String getPassword()  {
        return String.valueOf(password.getText()); }
    private String getRepassword() {
        return String.valueOf(repassword.getText()); }
    private String getName() {
        return String.valueOf(name.getText()); }
    private String getAge() {
        return String.valueOf(age.getText()); }



///    User input check .  email and password are REQUIRED, name and age are optional.
    protected boolean isValid() {     /// email format validation check with regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        if (getEmail().equals("") ) {
            RegisterActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(RegisterActivity.this, "Email is empty.", Toast.LENGTH_SHORT).show(); }}); }
        else if (!pat.matcher(getEmail()).matches()) {
            RegisterActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(RegisterActivity.this, "Invalid email.", Toast.LENGTH_SHORT).show(); }}); }
        else {
            if (getPassword().isEmpty()  || getRepassword().isEmpty()) {
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "Password is empty.", Toast.LENGTH_SHORT).show(); }}); }
            else if (!(getPassword().equals(getRepassword()))) {
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show(); }}); }
            else if (!(getPassword().length() > 5)) {
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "Passwords must be 6 or more characters in length.", Toast.LENGTH_SHORT).show(); }}); }
            else {
                return true; } }
        return false;
    }



///Register layout is divided in 2 sublayers.  subLayer2 and subLayer2. when one is show , the other is hidden.
    ///  The following subLayer methods swap their visibility.
    public void subLayer1(View view)  {
        if (isValid()){
            subLayout1.setVisibility(View.GONE);
            subLayout2.setVisibility(View.VISIBLE); }
    }
    public void subLayer2(View view){
        subLayout2.setVisibility(Button.GONE);
        subLayout1.setVisibility(View.VISIBLE); }

    void   uploadAvatar(){
    //// TODO: 12/10/2015  UPLOAD AVATAR FILE  +SHARED PREFERENCES.KTLP.
    }
}