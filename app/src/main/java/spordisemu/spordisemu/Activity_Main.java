package spordisemu.spordisemu;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Activity_Main extends AppCompatActivity {

    public final static String apiURL = "http://private-6358e-spordisemu1.apiary-mock.com";
    public final static String EXTRA_MESSAGE = "";
    public static boolean loggedIn = false;

    private AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.5F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        /*LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)nameEditText.getLayoutParams();
        params.setMargins(nameEditText.getLeft(), 0, height / 4, 0); //left, top, right, bottom
        nameEditText.setLayoutParams(params);*/
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void verifyUrl(View view) {

        view.startAnimation(buttonClick);
        loggedIn = false;

        //Button login = (Button) findViewById(R.id.login);
        //login.setBackgroundColor(Color.parseColor("#c7c7c7"));

        EditText nameEditText = (EditText) findViewById(R.id.nameId);
        String username = nameEditText.getText().toString();
        nameEditText.setTypeface(Typeface.SANS_SERIF);

        EditText passwordEditText = (EditText) findViewById(R.id.passwordId);
        String password = passwordEditText.getText().toString();
        passwordEditText.setTypeface(Typeface.SANS_SERIF);

        if( username != null && !username.isEmpty()) {

            String urlString = apiURL + "/users/" + username;
            new CallAPI().execute(urlString, password);

        }
    }

    public void registration (View view) {

        view.startAnimation(buttonClick);
        Intent registrationIntent = new Intent(getApplicationContext(), Activity_Registration.class);
        startActivity(registrationIntent);

    }

    private class CallAPI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            String password = params[1];
            String response = "Sisselogimine eba\u00f5nnestus";
            InputStream in;

            String strFileContents = null;
            JSONObject json;

            // HTTP Get
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());

                byte[] contents = new byte[1024];

                int bytesRead=0;
                while( (bytesRead = in.read(contents)) != -1){
                    strFileContents = new String(contents, 0, bytesRead);
                }
            } catch (Exception e ) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }

            try {
                json = new JSONObject(strFileContents);
                if (json.getString("password").equals(password)) {
                    loggedIn = true;

                    response = "Sisselogimine \u00f5nnestus";

                }
                else {
                    response = "Sisselogimine eba\u00f5nnestus";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String result) {
            Intent resultIntent = new Intent(getApplicationContext(), Activity_Result.class);
            resultIntent.putExtra(EXTRA_MESSAGE, result);
            startActivity(resultIntent);
        }
    }
}
