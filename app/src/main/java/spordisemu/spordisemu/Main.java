package spordisemu.spordisemu;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main extends ActionBarActivity {

    public final static String apiURL = "http://jsonplaceholder.typicode.com";
    public final static String EXTRA_MESSAGE = "com.example.webapitutorial.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        EditText idEditText = (EditText) findViewById(R.id.id);
        String id = idEditText.getText().toString();

        if( id != null && !id.isEmpty()) {

            String urlString = apiURL + "/posts/" + id;
            new CallAPI().execute(urlString);

        }

    }

    private class CallAPI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String urlString=params[0];
            String resultToDisplay;
            InputStream in = null;

            String strFileContents = null;
            JSONObject json = new JSONObject();

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
                return json.getString("body");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra(EXTRA_MESSAGE, result);
            startActivity(intent);
        }

    }
}
