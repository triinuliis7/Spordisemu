package spordisemu.spordisemu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Registration extends ActionBarActivity{

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        TextView title = (TextView) findViewById(R.id.signUpText);
        ViewGroup.MarginLayoutParams titleParams = (ViewGroup.MarginLayoutParams) title.getLayoutParams();

        EditText signUpText = (EditText) findViewById(R.id.signUpNameEdit);
        ViewGroup.MarginLayoutParams signUpTextParams = (ViewGroup.MarginLayoutParams) signUpText.getLayoutParams();

        EditText signUpMailEdit = (EditText) findViewById(R.id.signUpMailEdit);
        ViewGroup.MarginLayoutParams signUpMailParams = (ViewGroup.MarginLayoutParams) signUpMailEdit.getLayoutParams();

        EditText signUpPwEdit1 = (EditText) findViewById(R.id.signUpPwEdit1);
        ViewGroup.MarginLayoutParams signUpPwEdit1Params = (ViewGroup.MarginLayoutParams) signUpPwEdit1.getLayoutParams();

        EditText signUpPwEdit2 = (EditText) findViewById(R.id.signUpPwEdit2);
        ViewGroup.MarginLayoutParams signUpPwEdit2Params = (ViewGroup.MarginLayoutParams) signUpPwEdit2.getLayoutParams();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        TextView suguText = (TextView) findViewById(R.id.textView2);

        Button signUpBtn = (Button) findViewById(R.id.signUpBtn);
        ViewGroup.MarginLayoutParams signUpBtnParams = (ViewGroup.MarginLayoutParams) signUpBtn.getLayoutParams();

        LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout1);
        ViewGroup.MarginLayoutParams layout1Params = (ViewGroup.MarginLayoutParams) layout1.getLayoutParams();

        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        ViewGroup.MarginLayoutParams layout2Params = (ViewGroup.MarginLayoutParams) layout2.getLayoutParams();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            title.setTextSize(24);
            titleParams.topMargin = 10;
            signUpTextParams.topMargin = 5;
            signUpTextParams.height = 70;
            signUpBtnParams.topMargin = 10;
            signUpBtnParams.height = 70;
            layout1Params.height = 70;
            layout2Params.height = 70;
            signUpMailParams.height = 70;
            signUpPwEdit1Params.height = 70;
            signUpPwEdit2Params.height = 70;
        }
        else {
            titleParams.topMargin = height / 6;
        }

        ViewGroup.MarginLayoutParams radioGroupParams = (ViewGroup.MarginLayoutParams) radioGroup.getLayoutParams();

        dateView = (TextView) findViewById(R.id.textView4);
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

        /*LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) radioGroup.getLayoutParams();
        params.width = 250 - suguText.getWidth();
        radioGroup.setLayoutParams(params);*/
    }

    public void setDate(View view) {
        showDialog(999);
        //Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
        //        .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
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

    private void showDate(String year, String month, String day) {
        dateView.setText(new StringBuilder().append(day).append(".")
                .append(month).append(".").append(year));
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
