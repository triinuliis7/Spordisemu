package spordisemu.spordisemu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Triinu Liis on 09/10/2015.
 */
public class CreateOptionsLocationActivity extends AppCompatActivity  {

    private GoogleMap mMap;
    public static TextView location;

    private AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.5F);

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_options_location);

        ActionBar bar = getSupportActionBar();
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar3, null);

        bar.setCustomView(mCustomView);
        bar.setDisplayShowCustomEnabled(true);

        Intent intent = getIntent();

        location = (TextView) findViewById(R.id.textView4);
        location.setText("Eesti");
    }

    public static void setText(String text) {
        location.setText(text);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void openMap (View view) {
        view.startAnimation(buttonClick);
        Intent mapIntent = new Intent(getApplicationContext(), Map.class);
        startActivity(mapIntent);

    }

    public void createHome (View view) {
        view.startAnimation(buttonClick);
        postProfile();
        postSport();
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        //startActivity(homeIntent);

    }

    public JSONObject makeJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", getIntent().getStringExtra("user_id"));
            json.put("location", getIntent().getStringExtra("location"));
            json.put("pic", getIntent().getStringExtra("img"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void postProfile() {
        JSONObject json = makeJson();
        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/profiles/" + getIntent().getStringExtra("username");
        new CallAPI().execute(urlString, json.toString());
    }

    public void postSport() {
        JSONObject json = new JSONObject();
        try {
            json.put("user_id", getIntent().getStringExtra("user_id"));
            json.put("username", getIntent().getStringExtra("username"));
            json.put("sports", getIntent().getStringExtra("sports"));
            json.put("level", getIntent().getStringExtra("level"));
            json.put("pic", getIntent().getStringExtra("img"));

            String apiURL = getResources().getString(R.string.apiUrl);
            String urlString = apiURL + "/users/" + getIntent().getStringExtra("username");
            new CallAPI().execute(urlString, json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CallAPI extends AsyncTask<String, String, String> {

        ProgressDialog dialog = new ProgressDialog(CreateOptionsLocationActivity.this);

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
            String response;

            StringBuffer jsonResponse = new StringBuffer();

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

            response = jsonResponse.toString();
            return response;
        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
            Intent HomeActivityIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(HomeActivityIntent);
        }
    }

}
