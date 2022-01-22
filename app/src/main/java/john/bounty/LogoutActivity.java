package john.bounty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


public class LogoutActivity extends Activity{

    private TextView  logname;
    SharedPreferences sharedprefs;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        TextView logemail = (TextView) findViewById(R.id.logemail);
        TextView logage = (TextView) findViewById(R.id.logage);
        logname = (TextView) findViewById(R.id.logname);

        sharedprefs= getSharedPreferences(String.valueOf(R.string.myPrefs), MODE_PRIVATE);
        logemail.setText(sharedprefs.getString(String.valueOf(R.string.Email), ""));
        logage.setText(sharedprefs.getString(String.valueOf(R.string.Age), ""));

    }




public void  signatureName(View view){

    String font = "font/scriptina_pro.ttf";

    Typeface tf = Typeface.createFromAsset(getAssets(), font);
    logname.setTypeface(tf);
    logname.setText(sharedprefs.getString(String.valueOf(R.string.Name), ""));
}









    public void hideKeyboardLogout(View view)
    {
        InputMethodManager immRegister = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        immRegister.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void LoginActivity(View view) {
        SharedPreferences.Editor editor = sharedprefs.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}