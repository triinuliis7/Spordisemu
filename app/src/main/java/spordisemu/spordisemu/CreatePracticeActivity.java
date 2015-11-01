package spordisemu.spordisemu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Kelian on 31/10/2015.
 */
public class CreatePracticeActivity extends FragmentActivity {

    static Spinner sportSpinner;
    static Spinner raskustaseSpinner;

    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    private TextView timeView;
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
                android.R.layout.simple_spinner_item);
        sportItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSpinner.setAdapter(sportItems);

        //raskustaseme dropdown
        raskustaseSpinner = (Spinner)findViewById(R.id.raskustase);
        ArrayAdapter<CharSequence> raskustaseItems = ArrayAdapter.createFromResource(this, R.array.raskustaseList,
                android.R.layout.simple_spinner_item);
        raskustaseItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raskustaseSpinner.setAdapter(raskustaseItems);


        dateView = (TextView) findViewById(R.id.date);
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

        timeView = (TextView) findViewById(R.id.time);
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

    public void setDate(View view) {
        showDialog(999);
    }

    public void setTime(View view) {
        showDialog(998);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        if (id == 998) {
            return new TimePickerDialog(this, myTimeListener, hour, minute, DateFormat.is24HourFormat(this));
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            String arg1S = Integer.toString(arg1); //year
            String arg2S = Integer.toString(arg2 + 1); //month
            String arg3S = Integer.toString(arg3); //day

            if (arg3 < 10) {
                arg3S = "0" + Integer.toString(arg3);
            }
            if (arg2 < 9) {
                arg2S = "0" + Integer.toString(arg2 + 1);
            }
            showDate(arg1S, arg2S, arg3S);
        }
    };

    private TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
            String arg1S = Integer.toString(arg1); //hour
            String arg2S = Integer.toString(arg2); //minute

            if (arg1 < 10) {
                arg1S = "0" + Integer.toString(arg1);
            }
            if (arg2 < 10) {
                arg2S = "0" + Integer.toString(arg2);
            }
            showTime(arg1S, arg2S);
        }
    };

    private void showDate(String year, String month, String day) {
        dateView.setText(new StringBuilder().append(day).append(".").append(month).append(".").append(year));
    }

    private void showTime(String hour, String minute) {
        timeView.setText(new StringBuilder().append(hour).append(":").append(minute));
    }
}

