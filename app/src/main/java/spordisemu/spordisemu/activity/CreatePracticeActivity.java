package spordisemu.spordisemu.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import spordisemu.spordisemu.widget.LoggedIn;
import spordisemu.spordisemu.R;

/**
 * Created by Kelian on 31/10/2015.
 */
public class CreatePracticeActivity extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.5F);

    static Spinner sportSpinner;
    static Spinner raskustaseSpinner;

    static EditText hour;
    static EditText minutes;

    static EditText day;
    static EditText month;
    static EditText year;

    static EditText location;

    static EditText min;
    static EditText max;

    static RadioGroup gender;

    public Intent PracticeViewIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //sets title of menu
        setTitle(R.string.trenn);

        setContentView(R.layout.activity_create_practice);

        PracticeViewIntent = new Intent(getApplicationContext(), PracticeViewActivity.class);

        //Spordialade dropdown
        sportSpinner = (Spinner) findViewById(R.id.sport);
        ArrayAdapter<CharSequence> sportItems = ArrayAdapter.createFromResource(this, R.array.sportList,
                R.layout.item_spinner);
        sportItems.setDropDownViewResource(R.layout.item_spinner_dropdown);
        sportSpinner.setAdapter(sportItems);

        //raskustaseme dropdown
        raskustaseSpinner = (Spinner)findViewById(R.id.raskustase);
        ArrayAdapter<CharSequence> raskustaseItems = ArrayAdapter.createFromResource(this, R.array.raskustaseList,
                R.layout.item_spinner);
        raskustaseItems.setDropDownViewResource(R.layout.item_spinner_dropdown);
        raskustaseSpinner.setAdapter(raskustaseItems);

        gender = (RadioGroup) findViewById(R.id.gender);

        hour = (EditText) findViewById(R.id.hour);
        minutes = (EditText) findViewById(R.id.minutes);

        day = (EditText) findViewById(R.id.day);
        month = (EditText) findViewById(R.id.month);
        year = (EditText) findViewById(R.id.year);

        location = (EditText) findViewById(R.id.location);
        min = (EditText) findViewById(R.id.min);
        max = (EditText) findViewById(R.id.max);

    }

    public void createPractice(View view) {
        view.startAnimation(buttonClick);
        if (checkIfDataCorrect()){
            String timestamp = generateTimestamp();
            JSONObject json = new JSONObject();
            try {
                json.put("type", sportSpinner.getSelectedItem().toString());
                json.put("timestamp", timestamp);
                json.put("location", location.getText().toString());
                json.put("level", raskustaseSpinner.getSelectedItem().toString());
                json.put("user_id", LoggedIn.id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            postJson(json);
        }

    }

    public boolean checkIfDataCorrect() {
        boolean done = true;
        if (checkIfSportsEmpty()) {
            done = false;
        }

        if (checkFieldNotCorrect(hour, 24))  {
            done = false;
        }
        if (checkFieldNotCorrect(minutes, 60)) {
            done = false;
        }
        if (checkIfEmpty(day)) {
            done = false;
        }
        if (checkIfEmpty(month)) {
            done = false;
        }
        if (checkTimeNotCorrect()) {
            done = false;
        }
        if (checkIfEmpty(location)) {
            done = false;
        }
        if (checkMinMaxNotCorrect()) {
            done = false;
        }
        return done;

    }

    public boolean checkIfSportsEmpty() {
        if(sportSpinner.getSelectedItem().toString().equals("Spordiala") ||
                raskustaseSpinner.getSelectedItem().toString().equals( "Tase")){
            AlertDialog.Builder alert = new AlertDialog.Builder(CreatePracticeActivity.this);
            alert.setMessage(getResources().getString(R.string.valimataSport));
            alert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = alert.create();
            alert11.show();

            return true;
        }

        return false;
    }

    public boolean checkIfEmpty(EditText name) {
        if (name.getText().length() == 0) {
            name.setError(getResources().getString(R.string.fill));
            return true;
        }
        return false;
    }

    public boolean checkFieldNotCorrect(EditText name, int value) {
        if (!checkIfEmpty(name)){
            int number = Integer.parseInt(name.getText().toString());
            if(number > value) {
                name.setError(getResources().getString(R.string.timestampError));
                return true;
            }
        }
        return false;
    }

    public boolean checkTimeNotCorrect() {
        // poolik
        if (!checkIfEmpty(year)) {
            int yyyy = Integer.parseInt(year.getText().toString());
            if (yyyy < 2015) {
                year.setError(getResources().getString(R.string.yearError));
                return true;
            }
        }
        return false;
    }

    public boolean checkMinMaxNotCorrect() {
        boolean done = false;
        if(min.getText().length() != 0) {
            int minnr = Integer.parseInt(min.getText().toString());
            if (minnr < 1) {
                min.setError(getResources().getString(R.string.minError));
                done = true;
            }
        }
        if (max.getText().length() != 0) {
            int maxnr = Integer.parseInt(max.getText().toString());

            if (maxnr < 1) {
                max.setError(getResources().getString(R.string.minError));
                done = true;

            } else if (min.getText().length() != 0) {
                int minnr = Integer.parseInt(min.getText().toString());
                if(maxnr < (minnr + 1)) {
                    max.setError(getResources().getString(R.string.maxError));
                    done = true;
                }
            }
        }
        return done;
    }

    public String generateTimestamp() {
        String timestamp = year.getText().toString() + "-" + month.getText().toString() + "-" +
                day.getText().toString() + " " + hour.getText().toString() + ":" +
                minutes.getText().toString() + ":00";
        return timestamp;
    }

    public int getGender() {
        int RadioButtonID = gender.getCheckedRadioButtonId();
        View radioButton = gender.findViewById(RadioButtonID);
        return gender.indexOfChild(radioButton);
    }

    public int getMinMax(EditText name) {
        int result = 0;
        if(name.getText().length() != 0){
            result = Integer.parseInt(name.getText().toString());
        }
        return result;
    }

    public void postJson(JSONObject json) {
        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/practices";
        new CallAPI().execute(urlString, json.toString(), "createPractice", "post");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public void initializeAdditional(JSONObject json) {
        try {

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("practice_id", json.getString("practice_id"));
            jsonRequest.put("gender", getGender());
            jsonRequest.put("min", getMinMax(min));
            jsonRequest.put("max", getMinMax(max));
            String apiURL = getResources().getString(R.string.apiUrl);
            String urlString = apiURL + "/additional";
            new CallAPI().execute(urlString, jsonRequest.toString(), "info", "post");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            alertMessage();
            return true;
        }
        return false;
    }

    // warns user of canceling creating a practice
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            alertMessage();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onCancelClick(View view) {
        alertMessage();
    }

    public void alertMessage() {
        AlertDialog.Builder alert = new AlertDialog.Builder(CreatePracticeActivity.this);
        alert.setMessage(getResources().getString(R.string.backPractice));
        alert.setNegativeButton("Jah",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent HomeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        HomeIntent.putExtra("loggedIn_id", LoggedIn.id);
                        startActivity(HomeIntent);
                    }
                });
        alert.setPositiveButton("Ei",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = alert.create();
        alert11.show();
    }


    private class CallAPI extends AsyncTask<String, String, String> {

        ProgressDialog dialog = new ProgressDialog(CreatePracticeActivity.this);

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
            String type = params[2];
            String getpost = params[3];
            String response;

            StringBuffer jsonResponse = new StringBuffer();

            if(getpost.equals("post")) {
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
                } catch (IOException e ) {
                    System.out.println(e.getMessage());
                    return "vale post" + request;
                }
            } else if (getpost.equals("get")) {
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
                } catch (IOException e ) {
                    System.out.println(e.getMessage());
                    return e.getMessage();
                }
            }

            response = jsonResponse.toString();
            if(type.equals("createPractice")) {
                response = "c" + response;
            } else if (type.equals("practice")) {
                response = "p" + response;
            } else if (type.equals("info")) {
                response = "i" + response;
            }
            return response;
        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
            if(result.charAt(0) == 'c') {
                try {
                    JSONObject json = new JSONObject(result.substring(2, result.length()));
                    PracticeViewIntent.putExtra("title", json.getString("type"));
                    PracticeViewIntent.putExtra("date", json.getString("timestamp"));
                    PracticeViewIntent.putExtra("location", json.getString("location"));
                    PracticeViewIntent.putExtra("level", json.getString("level"));
                    PracticeViewIntent.putExtra("user", LoggedIn.firstname + " " + LoggedIn.lastname);
                    String apiURL = getResources().getString(R.string.apiUrl);
                    String urlString = apiURL + "/practices/" + json.get("practice_id").toString();
                    new CallAPI().execute(urlString, "", "practice", "get");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if(result.charAt(0) == 'p') {
                try {
                    initializeAdditional(new JSONObject(result.substring(2, result.length())));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }  else if (result.charAt(0) == 'i') {
                dialog.dismiss();
                try {
                    JSONObject json = new JSONObject(result.substring(2, result.length()));
                    PracticeViewIntent.putExtra("min", json.getString("min").toString());
                    PracticeViewIntent.putExtra("max", json.getString("max").toString());
                    PracticeViewIntent.putExtra("gender", json.getString("gender").toString());
                    PracticeViewIntent.putExtra("practice_id", json.getString("practice_id").toString());
                    startActivity(PracticeViewIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


