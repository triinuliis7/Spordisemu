package spordisemu.spordisemu;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Triinu Liis on 09/10/2015.
 */
public class CreateProfileLocation extends AppCompatActivity  {

    private GoogleMap mMap;
    public static TextView location;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_profile_location);

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

        Intent mapIntent = new Intent(getApplicationContext(), Map.class);
        startActivity(mapIntent);

    }

    public void createHome (View view) {

        Intent homeIntent = new Intent(getApplicationContext(), Home.class);
        startActivity(homeIntent);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
