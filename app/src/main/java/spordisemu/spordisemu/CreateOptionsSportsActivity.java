package spordisemu.spordisemu;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
                R.layout.item_spinner);
        sportItems.setDropDownViewResource(R.layout.item_spinner_dropdown);
        sportSpinner.setAdapter(sportItems);

        //raskustaseme dropdown
        raskustaseSpinner = (Spinner)findViewById(R.id.raskustase1);
        ArrayAdapter<CharSequence> raskustaseItems = ArrayAdapter.createFromResource(this, R.array.raskustaseList,
                R.layout.item_spinner);
        raskustaseItems.setDropDownViewResource(R.layout.item_spinner_dropdown);
        raskustaseSpinner.setAdapter(raskustaseItems);

    }

    public void addSports (View view) {
        view.startAnimation(buttonClick);

        if(sportSpinner.getSelectedItem().toString().equals( "Spordiala" ) ||
                raskustaseSpinner.getSelectedItem().toString().equals( "Tase")){
            AlertDialog.Builder alert = new AlertDialog.Builder(CreateOptionsSportsActivity.this);
            alert.setMessage(getResources().getString(R.string.valimataSport));
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
        }
    }


    public void createLocation (View view) {
        view.startAnimation(buttonClick);
        TextView added = (TextView) findViewById(R.id.textView6);

        if (sportSpinner.getSelectedItem().toString().equals( "Spordialad" ) ||
                raskustaseSpinner.getSelectedItem().toString().equals( "Tasemed")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(CreateOptionsSportsActivity.this);
            alert.setMessage(getResources().getString(R.string.valimataSport));
            alert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = alert.create();
            alert11.show();
        } else if (added.getVisibility() == (View.INVISIBLE)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(CreateOptionsSportsActivity.this);
            alert.setMessage(getResources().getString(R.string.vajutamataNupp));
            alert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = alert.create();
            alert11.show();
        } else {
            Intent createOptionsLocationIntent = new Intent(getApplicationContext(), CreateOptionsLocationActivity.class);
            createOptionsLocationIntent.putExtra("sports", sportSpinner.getSelectedItem().toString());
            createOptionsLocationIntent.putExtra("level", raskustaseSpinner.getSelectedItem().toString());
            startActivity(createOptionsLocationIntent);
        }

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
