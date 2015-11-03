package spordisemu.spordisemu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Kelian on 31/10/2015.
 */
public class CreatePracticeActivity extends AppCompatActivity {

    static Spinner sportSpinner;
    static Spinner raskustaseSpinner;

    private Calendar calendar;
    private EditText dateView;
    private int year, month, day;

    private EditText timeView;
    private int hour, minute;

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


        dateView = (EditText) findViewById(R.id.date);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        String dayS = Integer.toString(day);
        String monthS = Integer.toString(month + 1);
        String yearS = Integer.toString(year);

        if (day < 10) {
            dayS = "0" + Integer.toString(day);
        }
        if (month < 9) {
            monthS = "0" + Integer.toString(month);
        }
        showDate(yearS, monthS, dayS);

        timeView = (EditText) findViewById(R.id.time);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        String hourS = Integer.toString(hour);
        String minuteS = Integer.toString(minute);

        if (hour < 10) {
            hourS = "0" + Integer.toString(hour);
        }
        if (minute < 10) {
            minuteS = "0" + Integer.toString(minute);
        }

        showTime(hourS,minuteS);

    }

    private void showDate(String year, String month, String day) {
        //dateView.setText(new StringBuilder().append(day).append(".").append(month).append(".").append(year));
    }

    private void showTime(String hour, String minute) {
        //timeView.setText(new StringBuilder().append(hour).append(":").append(minute));
    }
}

