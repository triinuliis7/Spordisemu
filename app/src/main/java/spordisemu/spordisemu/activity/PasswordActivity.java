package spordisemu.spordisemu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import spordisemu.spordisemu.R;
import spordisemu.spordisemu.widget.LoggedIn;

/**
 * Created by Triinu Liis on 04/12/2015.
 */
public class PasswordActivity extends AppCompatActivity {

    public EditText new1;
    public EditText old;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(R.string.changePassword);

        setContentView(R.layout.activity_password);

    }

    public void changePassword (View view) {
        old = (EditText) findViewById(R.id.oldPassword);
        new1 = (EditText) findViewById(R.id.newPassword1);
        EditText new2 = (EditText) findViewById(R.id.newPassword2);

        if (!new1.getText().toString().equals(new2.getText().toString())) {
            new1.setError(getResources().getString(R.string.checkPw));
            new2.setError(getResources().getString(R.string.checkPw));
        }

        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/users/" + LoggedIn.username;
        new CallAPI().execute(urlString, "", "get");
    }

    private class CallAPI extends AsyncTask<String, String, String> {

        ProgressDialog dialog = new ProgressDialog(PasswordActivity.this);

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
            String request = params[1];
            String getpost = params[2];
            String response;

            StringBuffer jsonResponse = new StringBuffer();

            if (getpost.equals("get")) {
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
                response = "g" + jsonResponse.toString();
            } else {
                // HTTP POST
                try {
                    String line;
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
                    writer.write(request);
                    writer.close();
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                    while ((line = br.readLine()) != null) {
                        jsonResponse.append(line);
                    }
                    br.close();

                    urlConnection.disconnect();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    return request;
                }
                response = "p" + jsonResponse.toString();
            }
            return response;
        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
            if (result.charAt(0) == 'p') {
                Toast.makeText(getApplicationContext(), R.string.passwordIsChanged, Toast.LENGTH_SHORT).show();
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            } else {
                try {
                    JSONObject json = new JSONObject(result.substring(2, result.length()-1));
                    if (!json.getString("password").equals(old.getText().toString())) {
                        old.setError(getResources().getString(R.string.wrongPw));
                    } else {
                        String apiURL = getResources().getString(R.string.apiUrl);
                        String urlString = apiURL + "/users/" + LoggedIn.username + "/password";
                        JSONObject json2 = new JSONObject();
                        json2.put("password", new1.getText().toString());
                        new CallAPI().execute(urlString, json2.toString(), "post");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
