package spordisemu.spordisemu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Triinu Liis on 11/10/2015.
 */
public class HomeActivity extends BaseActivity {

    public String date = "";
    public String location = "";
    public String title = "";
    public String practice_id = "";
    public String level = "";
    public Intent PracticeViewIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getJson();

        super.onCreate(savedInstanceState);

        //for navigation drawer
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);


        if (LoggedIn.id == null) {
            Toast.makeText(getApplicationContext(), "Logi uuesti sisse", Toast.LENGTH_LONG).show();
        }

        PracticeViewIntent = new Intent(getApplicationContext(), PracticeViewActivity.class);
    }

    //nuppu vajutades, id näitab positsiooni listis
    public void detailedView(int id) {
        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/practices/" + id;
        new CallAPI().execute(urlString, "practice");
    }

    //tehakse kohe alguses
    public void getJson() {
        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/practices";
        new CallAPI().execute(urlString, "practices");
    }

    protected String parseDate(String date) {
        String[] dateTime = date.split(" ");
        String[] dates = dateTime[0].split("-");
        String[] times = dateTime[1].split(":");
        String dateNew = dates[2] + "." + dates[1] + "." + dates[0];
        dateNew += " " + times[0] + ":" + times[1];
        return dateNew;
    }

    public void initializeVariables(JSONArray json) {
        int length = json.length();
        ListView sports_list;
        HashMap<String, Integer> icons = new HashMap<String, Integer>();
        icons.put("Jalgpall", R.drawable.football);
        icons.put("Korvpall", R.drawable.basketball);
        icons.put("Sulgpall", R.drawable.tennis);
        //icons.put("Jõusaal", R.drawable.gym);
        icons.put("Jooga", R.drawable.jooga);

        String[] sportsArray = new String[length];
        String[] dateArray = new String[length];
        String[] locationArray = new String[length];
        Integer[] images = new Integer[length];
        final Integer[] ids = new Integer[length];
        for (int i = 0; i < length; i++) {
            try {
                sportsArray[i] = json.getJSONObject(i).get("type").toString();
                dateArray[i] = parseDate(json.getJSONObject(i).get("timestamp").toString());
                locationArray[i] = json.getJSONObject(i).get("location").toString();
                images[i] = icons.get(json.getJSONObject(i).get("type").toString());
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
            String urlString = apiURL + "/users/id/" + id;
            new CallAPI().execute(urlString, "user");

            date = json.get("timestamp").toString();
            location = json.getString("location").toString();
            title = json.getString("type").toString();
            level = json.getString("level").toString();
            practice_id = json.getString("practice_id").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initializeUser(JSONObject json) {
        try {
            PracticeViewIntent.putExtra("user", json.getString("firstname").toString() + " " + json.getString("lastname").toString());
            PracticeViewIntent.putExtra("date", date);
            PracticeViewIntent.putExtra("location", location);
            PracticeViewIntent.putExtra("title", title);
            PracticeViewIntent.putExtra("level", level);
            PracticeViewIntent.putExtra("practice_id", practice_id);

            //startActivity(PracticeViewIntent);

            String apiURL = getResources().getString(R.string.apiUrl);
            String urlString = apiURL + "/additional/" + practice_id;
            new CallAPI().execute(urlString, "info");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initializeInfo(JSONObject json) {
        try {
            PracticeViewIntent.putExtra("min", json.getString("min").toString());
            PracticeViewIntent.putExtra("max", json.getString("max").toString());
            PracticeViewIntent.putExtra("gender", json.getString("gender").toString());
            startActivity(PracticeViewIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void createPracticeActivity (View view) {

        Intent practiceIntent = new Intent(getApplicationContext(), CreatePracticeActivity.class);
        if (LoggedIn.id == null) {
            Toast.makeText(getApplicationContext(), "Logi uuesti sisse", Toast.LENGTH_LONG).show();
        }
        startActivity(practiceIntent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // do nothing
            return true;
        }
        return super.onKeyDown(keyCode,event);
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
            } else if (type.equals("user")) {
                response = "u" + response;
            } else if (type.equals("info")) {
                response = "i" + response;
            }
            return response;
        }

        protected void onPostExecute(String result) {
            if (result.charAt(0) == 'p') {
                try {
                    initializePractice(new JSONObject(result.substring(2, result.length())));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (result.charAt(0) == 'u') {
                try {
                    initializeUser(new JSONObject(result.substring(2, result.length())));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (result.charAt(0) == 'i') {
                dialog.dismiss();
                try {
                    initializeInfo(new JSONObject(result.substring(2, result.length())));
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
