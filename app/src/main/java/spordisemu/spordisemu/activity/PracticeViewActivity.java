package spordisemu.spordisemu.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import spordisemu.spordisemu.widget.LoggedIn;
import spordisemu.spordisemu.R;
import spordisemu.spordisemu.widget.RoundedImageView;
import spordisemu.spordisemu.adapter.CommentsListAdapter;

/**
 * Created by ingrid on 10/25/15.
 */
public class PracticeViewActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private boolean chooseMarker = false;
    private Address address;
    private Button btn;
    boolean isAttending;
    String attend_id;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_practice_view);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        EditText commentText = (EditText) findViewById(R.id.newComment);
        commentText.clearFocus();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(getIntent().getStringExtra("title"));

        //logged in users icon
        RoundedImageView newCommentIcon = (RoundedImageView) findViewById(R.id.newCommentIcon);
        newCommentIcon.setImageDrawable(getDrawable(R.drawable.man));

        btn = (Button) findViewById(R.id.attend);
        btn.setVisibility(View.VISIBLE);

        changeButtonIfNeeded();
        setUpMapIfNeeded();
        getComments();

        TextView date = (TextView) findViewById(R.id.date);
        String dateText = parseDate(getIntent().getStringExtra("date"), false);
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

    public void addComment(View view) {
        JSONObject json = new JSONObject();
        EditText commentText = (EditText) findViewById(R.id.newComment);
        if (commentText.getText().length() != 0) {
            try {
                json.put("user_id", LoggedIn.id);
                json.put("practice_id", getIntent().getStringExtra("practice_id"));
                json.put("comment", commentText.getText());

                String apiURL = getResources().getString(R.string.apiUrl);
                String urlString = apiURL + "/comments";
                new CallAPI().execute(urlString, "post", json.toString(), "comments");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.commentTooShort), Toast.LENGTH_SHORT).show();
        }
    }

    protected void changeButtonIfNeeded() {
        String practice_id = getIntent().getStringExtra("practice_id");
        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/attends/" + practice_id;
        new CallAPI().execute(urlString, "get", "", "attends");
    }

    protected void getComments() {
        String practice_id = getIntent().getStringExtra("practice_id");
        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/comments/" + practice_id;
        new CallAPI().execute(urlString, "get", "", "comments");
    }

    public void initializeComments(JSONArray json) {
        int length = json.length();
        ListView comments_list;

        String[] usernames = new String[length];
        String[] contents = new String[length];
        Drawable[] images = new Drawable[length];
        String[] dates = new String[length];
        final Integer[] ids = new Integer[length];
        for (int i = 0; i < length; i++) {
            try {
                usernames[i] = json.getJSONObject(i).get("username").toString();
                contents[i] = json.getJSONObject(i).get("comment").toString();
                dates[i] = "(" + parseDate(json.getJSONObject(i).get("timestamp").toString(), true) + ")";
                images[i] = getResources().getDrawable(R.drawable.man);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        CommentsListAdapter adapter = new CommentsListAdapter(this, usernames, images, dates, contents);
        comments_list = (ListView) findViewById(R.id.comments_list);
        comments_list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(comments_list);
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    protected String parseDate(String date, boolean isComment) {
        String[] dateTime = date.split(" ");
        String[] dates = dateTime[0].split("-");
        String[] times = dateTime[1].split(":");
        //String dateNew = times[0] + ":" + times[1];
        String dateNew;
        if (isComment) {
            dateNew = dates[2] + "." + dates[1] + "." + dates[0];
        } else {
            dateNew = times[0] + ":" + times[1] +
                    " " + dates[2] + "." + dates[1] + "." + dates[0];
        }
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
        if (!isAttending) {
            alert.setMessage(getResources().getString(R.string.kinnitaOsalemine));
            alert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String loggedIn_id = LoggedIn.id;
                            String practice_id = getIntent().getStringExtra("practice_id");
                            String apiURL = getResources().getString(R.string.apiUrl);
                            String urlString = apiURL + "/attends";
                            JSONObject json = new JSONObject();
                            try {
                                json.put("user_id", loggedIn_id);
                                json.put("practice_id", practice_id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            isAttending = true;
                            new CallAPI().execute(urlString, "post", json.toString(), "newAttend");
                        }
                    });
            alert.setNegativeButton(getResources().getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        // kui juba osaleb, küsi kas tahab lahkuda
        } else {
            alert.setMessage(getResources().getString(R.string.kinnitaLahkumine));
            alert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String apiURL = getResources().getString(R.string.apiUrl);
                            String urlString = apiURL + "/attends/" + attend_id;
                            new CallAPI().execute(urlString, "delete", "", "deleteAttend");
                        }
                    });
            alert.setNegativeButton(getResources().getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        }

        AlertDialog alert11 = alert.create();
        alert11.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            String type = params[3];
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
                    return jsonResponse.toString();
                }
                if (type.equals("comments")) {
                    response = "c" + jsonResponse.toString();
                } else {
                    response = jsonResponse.toString();
                }
                response = "p" + response;
            } else if (getpost.equals("get")){
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
                if (type.equals("comments")) {
                    response = "c" + jsonResponse.toString();
                } else {
                    response = "a" + jsonResponse.toString();
                }
            } else {
                try {
                    String line;
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setRequestMethod("DELETE");
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
                response = "d" + jsonResponse.toString();
            }
            return response;
        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
            if (result.length() != 0) {
                //POST
                if (result.charAt(0) == 'p') {
                    if (result.charAt(1) == 'c') {
                        EditText commentText = (EditText) findViewById(R.id.newComment);
                        commentText.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(commentText.getWindowToken(), 0);
                        getComments();
                    } else {
                        try {
                            JSONObject json = new JSONObject(result.substring(2, result.length()-1));
                            attend_id = json.getString("attend_id");

                            AlertDialog.Builder alert = new AlertDialog.Builder(PracticeViewActivity.this);
                            alert.setMessage(getResources().getString(R.string.success));
                            alert.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            btn.setText(getResources().getString(R.string.lahku));
                                            btn.setBackgroundColor(Color.parseColor("#f5cece"));
                                            btn.setTextColor(Color.parseColor("#222222"));
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = alert.create();
                            alert11.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (result.charAt(0) == 'a') {
                    try {
                        //kontrolli alguses, kas osaleb treeningul
                        JSONArray jsonArray = new JSONArray(result.substring(1, result.length()));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if (jsonArray.getJSONObject(i).getString("user_id").equals(LoggedIn.id)) {
                                isAttending = true;
                                attend_id = jsonArray.getJSONObject(i).getString("attend_id");
                            }
                        }
                        if (isAttending) {
                            btn.setText(getResources().getString(R.string.lahku));
                            btn.setBackgroundColor(Color.parseColor("#f5cece"));
                            btn.setTextColor(Color.parseColor("#222222"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (result.charAt(0) == 'c') {
                    try {
                        initializeComments(new JSONArray(result.substring(1, result.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (result.charAt(0) == 'd') {
                    isAttending = false;
                    btn.setText(getResources().getString(R.string.osale));
                    btn.setBackgroundColor(Color.parseColor("#40bf40"));
                    btn.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        }
    }
}
