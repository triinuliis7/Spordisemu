package spordisemu.spordisemu.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import spordisemu.spordisemu.widget.LoggedIn;
import spordisemu.spordisemu.R;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "";
    public static boolean loggedIn = false;

    private AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.5F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        TextView title = (TextView) findViewById(R.id.textView);
        ViewGroup.MarginLayoutParams titleParams = (ViewGroup.MarginLayoutParams) title.getLayoutParams();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            titleParams.topMargin = height / 10;
        }
        else {
            titleParams.topMargin = height / 5;
        }

        EditText editText = (EditText) findViewById(R.id.nameId);
        ViewGroup.MarginLayoutParams editTextParams = (ViewGroup.MarginLayoutParams) editText.getLayoutParams();
        editTextParams.topMargin = height / 15;

    }

    public void verifyUrl(View view) {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        view.startAnimation(buttonClick);
        loggedIn = false;

        EditText nameEditText = (EditText) findViewById(R.id.nameId);
        String username = nameEditText.getText().toString();
        nameEditText.setTypeface(Typeface.SANS_SERIF);

        EditText passwordEditText = (EditText) findViewById(R.id.passwordId);
        String password = passwordEditText.getText().toString();
        passwordEditText.setTypeface(Typeface.SANS_SERIF);

        if( username != null && !username.isEmpty()) {

            String apiURL = getResources().getString(R.string.apiUrl);
            String urlString = apiURL + "/users/" + username;
            new CallAPI().execute(urlString, password);

        }
    }

    //click on "Registreeru" button
    public void registration (View view) {

        view.startAnimation(buttonClick);
        Intent registrationIntent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(registrationIntent);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    private class CallAPI extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(getResources().getString(R.string.wait));
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            String password = params[1];
            String response = "f";

            StringBuffer jsonResponse = new StringBuffer();

            // HTTP GET
            try {
                String line;
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = br.readLine()) != null) {
                    jsonResponse.append(line);
                }
                br.close();
                urlConnection.disconnect();
            } catch (Exception e ) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }
            try {
                // viimane osa on vajalik, kuna json on array kujul!!!
                JSONObject json = new JSONObject(jsonResponse.toString().substring(1, jsonResponse.length()));
                if (json.getString("password").equals(password)) {
                    response = "t" + json.toString();
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
            if (result.charAt(0) == 't'){
                Intent loginIntent = new Intent(getApplicationContext(), HomeActivity.class);
                try {
                    LoggedIn.id = new JSONObject(result.substring(1)).getString("id");
                    LoggedIn.firstname = new JSONObject(result.substring(1)).getString("firstname");
                    LoggedIn.lastname = new JSONObject(result.substring(1)).getString("lastname");
                    if (LoggedIn.id != null) {
                        ParsePush.subscribeInBackground(LoggedIn.id.toString());
                        List<String> subscribedChannels = ParseInstallation.getCurrentInstallation().getList("channels");
                        //Toast.makeText(getApplicationContext(), subscribedChannels.get(0), Toast.LENGTH_LONG).show();
                        startActivity(loginIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Kasutajanimi või parool on vale");
                alert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = alert.create();
                alert11.show();
            }
        }
    }
}
