package spordisemu.spordisemu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    static Calendar calendar;

    static EditText location;

    static EditText min;
    static EditText max;

    static RadioGroup gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //sets title of menu
        setTitle(R.string.trenn);

        setContentView(R.layout.activity_create_practice);

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
            JSONObject json = new JSONObject();
            try {
                json.put("sports", sportSpinner.getSelectedItem().toString());
                json.put("level", raskustaseSpinner.getSelectedItem().toString());
                // poolik
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean checkIfDataCorrect() {
        boolean done = true;
        if (checkIfSportsEmpty()) {
            done = false;
        }
        if (checkFieldNotCorrect(hour, 12)) {
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
        if(sportSpinner.getSelectedItem().toString().equals( "Spordiala" ) ||
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

            return false;
        }

        return true;
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
            if (yyyy < 2014) {
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

}

