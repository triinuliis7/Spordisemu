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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        TextView added = (TextView) findViewById(R.id.textView6);
        added.setVisibility(View.VISIBLE);
        TextView addedSport = (TextView) findViewById(R.id.sportsText);
        addedSport.setText(sportSpinner.getSelectedItem().toString() + ": " +
                raskustaseSpinner.getSelectedItem().toString());
    }


    public void createLocation (View view) {
        view.startAnimation(buttonClick);
        Intent createOptionsLocationIntent = new Intent(getApplicationContext(), CreateOptionsLocationActivity.class);
        startActivity(createOptionsLocationIntent);

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
