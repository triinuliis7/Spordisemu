package spordisemu.spordisemu;

import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ingrid on 10/9/15.
 */
public class CreateOptionsSportsActivity extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.5F);
    static Spinner sportSpinner;
    static Spinner raskustaseSpinner;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_options_sports);

        ActionBar bar = getSupportActionBar();
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar2, null);

        bar.setCustomView(mCustomView);
        bar.setDisplayShowCustomEnabled(true);

        Intent intent = getIntent();

        //Spordialade dropdown
        sportSpinner = (Spinner) findViewById(R.id.sport);
        ArrayAdapter<CharSequence> sportItems = ArrayAdapter.createFromResource(this, R.array.sportList,
                android.R.layout.simple_spinner_item);
        sportItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSpinner.setAdapter(sportItems);

        //raskustaseme dropdown
        raskustaseSpinner = (Spinner)findViewById(R.id.raskustase);
        ArrayAdapter<CharSequence> raskustaseItems = ArrayAdapter.createFromResource(this, R.array.raskustaseList,
                android.R.layout.simple_spinner_item);
        raskustaseItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raskustaseSpinner.setAdapter(raskustaseItems);

    }

    public void addSports (View view) {
        view.startAnimation(buttonClick);

        if(sportSpinner.getSelectedItem().toString().equals( "Spordialad" ) ||
                raskustaseSpinner.getSelectedItem().toString().equals( "Tasemed")){
            AlertDialog.Builder alert = new AlertDialog.Builder(CreateOptionsSportsActivity.this);
            alert.setMessage("Mingi väli on jäänud valimata");
            alert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = alert.create();
            alert11.show();
        }else {
            TextView added = (TextView) findViewById(R.id.textView6);
            added.setVisibility(View.VISIBLE);
            TextView addedSport = (TextView) findViewById(R.id.sportsText);
            addedSport.setText(sportSpinner.getSelectedItem().toString() + ": " +
                    raskustaseSpinner.getSelectedItem().toString());

            JSONObject json = new JSONObject();
            try {
                json.put("sports", sportSpinner.getSelectedItem().toString());
                json.put("level", raskustaseSpinner.getSelectedItem().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            postJson(json);

        }
    }


    public void createLocation (View view) {
        view.startAnimation(buttonClick);
        Intent createOptionsLocationIntent = new Intent(getApplicationContext(), CreateOptionsLocationActivity.class);
        startActivity(createOptionsLocationIntent);

    }

    public void postJson(JSONObject json) {
        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/users/test/sports";
        new CallAPI().execute(urlString, json.toString());
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

    }
}
