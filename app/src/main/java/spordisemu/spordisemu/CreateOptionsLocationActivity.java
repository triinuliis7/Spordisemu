package spordisemu.spordisemu;

import android.content.Intent;
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
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeIntent);

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

}
