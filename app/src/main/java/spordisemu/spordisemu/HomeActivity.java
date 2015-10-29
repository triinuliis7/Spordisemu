package spordisemu.spordisemu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Triinu Liis on 11/10/2015.
 */
public class HomeActivity extends AppCompatActivity {

    public String date = "";
    public String location = "";
    public String title = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getJson();

        super.onCreate(savedInstanceState);

        //sets title of menu
        setTitle(R.string.pealeht);

        setContentView(R.layout.activity_home);

    }

    public void detailedView(int id) {
        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/practices/" + id;
        new CallAPI().execute(urlString, "practice");
    }

    public void getJson() {
        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/practices";
        new CallAPI().execute(urlString, "practices");
    }

    public void initializeVariables(JSONArray json) {
        int length = json.length();
        ListView sports_list;
        String[] sportsArray = new String[length];
        String[] dateArray = new String[length];
        String[] locationArray = new String[length];
        Integer[] images = new Integer[length];
        final Integer[] ids = new Integer[length];
        for (int i = 0; i < length; i++) {
            try {
                sportsArray[i] = json.getJSONObject(i).get("type").toString();
                dateArray[i] = json.getJSONObject(i).get("timestamp").toString();
                locationArray[i] = json.getJSONObject(i).get("location").toString();
                images[i] = R.mipmap.ic_launcher;
                ids[i] = Integer.valueOf(json.getJSONObject(i).get("practice_id").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        CustomListAdapter adapter = new CustomListAdapter(this, sportsArray, dateArray, locationArray, images);
        sports_list = (ListView) findViewById(R.id.sports_list);
        sports_list.setAdapter(adapter);

        sports_list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                detailedView(ids[position]);
            }
        });
    }

    public void initializePractice(JSONObject json) {

        try {

            String id = json.getString("user_id").toString();

            String apiURL = getResources().getString(R.string.apiUrl);
            String urlString = apiURL + "/users/" + id;
            new CallAPI().execute(urlString, "user");

            date = json.get("timestamp").toString();
            location = json.getString("location").toString();
            title = json.getString("type").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initializeUser(JSONObject json) {
        Intent PracticeViewIntent = new Intent(getApplicationContext(), PracticeViewActivity.class);
        try {
            PracticeViewIntent.putExtra("user", json.getString("firstname").toString() + " " + json.getString("lastname").toString());
            PracticeViewIntent.putExtra("date", date);
            PracticeViewIntent.putExtra("location", location);
            PracticeViewIntent.putExtra("title", title);
            startActivity(PracticeViewIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(item.getItemId()) {
            case R.id.pealeht:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.profiil:
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.sobrad:
                Intent friendsIntent = new Intent(getApplicationContext(), FriendsActivity.class);
                startActivity(friendsIntent);
                break;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.logivalja:
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Midagi läks valesti :(",
                        Toast.LENGTH_SHORT).show();
                break;
        }

        //Return false to allow normal menu processing to proceed,
        //true to consume it here.
            return false;
    }

    private class CallAPI extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(HomeActivity.this);

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
            String type = params[1];
            String response;

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

            response = jsonResponse.toString();
            if (type.equals("practice")) {
                response = "p" + response;
            } else if (type.equals("user")){
                response = "u" + response;
            }
            return response;
        }

        protected void onPostExecute(String result) {
            if (result.charAt(0) == 'p') {
                try {
                    initializePractice(new JSONObject(result.substring(1)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (result.charAt(0) == 'u') {
                dialog.dismiss();
                try {
                    initializeUser(new JSONObject(result.substring(1)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                dialog.dismiss();
                JSONArray json = new JSONArray(result);
                initializeVariables(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
