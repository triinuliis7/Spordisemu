package spordisemu.spordisemu;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ingrid on 10/25/15.
 */
public class PracticeViewActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private boolean chooseMarker = false;
    private Address address;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_practice_view);

        setTitle(getIntent().getStringExtra("title"));

        setUpMapIfNeeded();

        changeButtonIfNeeded();

        TextView date = (TextView) findViewById(R.id.date);
        String dateText = parseDate(getIntent().getStringExtra("date"));
        date.setText(dateText);
        TextView location = (TextView) findViewById(R.id.location);
        location.setText(getIntent().getStringExtra("location"));
        TextView user = (TextView) findViewById(R.id.user);
        user.setText(getResources().getString(R.string.korraldaja) + " " + getIntent().getStringExtra("user"));

        TextView level = (TextView) findViewById(R.id.levelText);
        level.setText(getResources().getString(R.string.raskustase) + " " +
                getIntent().getStringExtra("level"));
        TextView min = (TextView) findViewById(R.id.attendeesText);
        min.setText(getResources().getString(R.string.osalejateArv) + " " +
                getIntent().getStringExtra("min") + " - " + getIntent().getStringExtra("max"));
        TextView gender = (TextView) findViewById(R.id.genderText);
        //
        String genderText = getIntent().getStringExtra("gender") == "1" ?
                getResources().getString(R.string.men) : getIntent().getStringExtra("gender") == "2" ?
                getResources().getString(R.string.women): getResources().getString(R.string.both);
        gender.setText(getResources().getString(R.string.eelistatudSugu) + " " + genderText);

    }

    protected void changeButtonIfNeeded() {
        String practice_id = getIntent().getStringExtra("practice_id");
        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/attends/" + practice_id;
        new CallAPI().execute(urlString, "get", "");
    }

    protected String parseDate(String date) {
        String[] dateTime = date.split(" ");
        String[] dates = dateTime[0].split("-");
        String[] times = dateTime[1].split(":");
        String dateNew = dates[2] + "." + dates[1] + "." + dates[0];
        dateNew += " " + times[0] + ":" + times[1];
        return dateNew;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.

            SupportMapFragment mMapFragment = (SupportMapFragment) (getSupportFragmentManager()
                    .findFragmentById(R.id.detailedMap));

            mMap = mMapFragment.getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        String locationString = getIntent().getStringExtra("location");
        LatLng latLng = getLocationFromAddress(this, locationString);
        try {
            mMap.addMarker(new MarkerOptions().position(latLng).title(locationString)).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "Aadressi " + locationString + " ei leidu!", Toast.LENGTH_LONG).show();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(58.653895, 25.532339), 6));
        }

    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public void attend(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(PracticeViewActivity.this);
        alert.setMessage(getResources().getString(R.string.kinnitaOsalemine));
        alert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String loggedIn_id = getIntent().getStringExtra("loggedIn_id");
                        String practice_id = getIntent().getStringExtra("practice_id");
                        String apiURL = getResources().getString(R.string.apiUrl);
                        String urlString = apiURL + "/attends/" + practice_id;
                        JSONObject json = new JSONObject();
                        try {
                            json.put("user_id", loggedIn_id);
                            json.put("practice_id", practice_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new CallAPI().execute(urlString, "post", json.toString());
                    }
                });
        alert.setNegativeButton(getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = alert.create();
        alert11.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
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
            case R.id.lisa_spordiala:
                Intent lisaIntent = new Intent(getApplicationContext(), AddSportsActivity.class);
                startActivity(lisaIntent);
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        R.string.wentWrong,
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    private class CallAPI extends AsyncTask<String, String, String> {

        ProgressDialog dialog = new ProgressDialog(PracticeViewActivity.this);

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
            String getpost = params[1];
            String request = params[2];
            String response;

            StringBuffer jsonResponse = new StringBuffer();

            // HTTP POST
            if (getpost.equals("post")) {
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
                    return "postis probleem";
                }
                response = "p" + jsonResponse.toString();
            } else {
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
                    return jsonResponse.toString();
                }
                response = "g" + jsonResponse.toString();
            }

            return response;
        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
            if (result.charAt(0) == 'p') {
                Toast.makeText(getApplicationContext(), result.substring(1), Toast.LENGTH_LONG).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(PracticeViewActivity.this);
                alert.setMessage(getResources().getString(R.string.success));
                alert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Button btn = (Button) findViewById(R.id.attend);
                                btn.setText(getResources().getString(R.string.juba_osaled));
                                btn.setEnabled(false);
                                btn.setBackgroundColor(Color.parseColor("#fff1f1f1"));
                                btn.setTextColor(Color.parseColor("#818081"));
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = alert.create();
                alert11.show();
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(result.substring(1, result.length()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if (jsonArray.getJSONObject(i).getString("user_id").equals(getIntent().getStringExtra("loggedIn_id"))) {
                            Button btn = (Button) findViewById(R.id.attend);
                            btn.setText(getResources().getString(R.string.juba_osaled));
                            btn.setEnabled(false);
                            btn.setBackgroundColor(Color.parseColor("#fff1f1f1"));
                            btn.setTextColor(Color.parseColor("#818081"));
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
