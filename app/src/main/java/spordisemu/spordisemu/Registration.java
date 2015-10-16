package spordisemu.spordisemu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Registration extends AppCompatActivity {

    public final static String apiURL = "http://private-6358e-spordisemu1.apiary-mock.com";

    static EditText signUpNameEdit;
    static EditText signUpLastEdit;
    static EditText signUpUserEdit;
    static EditText signUpMailEdit;
    static EditText signUpPwEdit1;
    static EditText signUpPwEdit2;

    private AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.5F);

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        int width = size.x;

        TextView title = (TextView) findViewById(R.id.signUpText);
        ViewGroup.MarginLayoutParams titleParams = (ViewGroup.MarginLayoutParams) title.getLayoutParams();

        signUpNameEdit = (EditText) findViewById(R.id.signUpNameEdit);
        ViewGroup.MarginLayoutParams signUpNameEditParams = (ViewGroup.MarginLayoutParams) signUpNameEdit.getLayoutParams();

        signUpLastEdit = (EditText) findViewById(R.id.SignUpLastEdit);
        ViewGroup.MarginLayoutParams signUpLastEditParams = (ViewGroup.MarginLayoutParams) signUpLastEdit.getLayoutParams();

        signUpUserEdit = (EditText) findViewById(R.id.SignUpUserEdit);
        ViewGroup.MarginLayoutParams signUpUserEditParams = (ViewGroup.MarginLayoutParams) signUpUserEdit.getLayoutParams();

        signUpMailEdit = (EditText) findViewById(R.id.signUpMailEdit);
        ViewGroup.MarginLayoutParams signUpMailParams = (ViewGroup.MarginLayoutParams) signUpMailEdit.getLayoutParams();

        signUpPwEdit1 = (EditText) findViewById(R.id.signUpPwEdit1);
        ViewGroup.MarginLayoutParams signUpPwEdit1Params = (ViewGroup.MarginLayoutParams) signUpPwEdit1.getLayoutParams();

        signUpPwEdit2 = (EditText) findViewById(R.id.signUpPwEdit2);
        ViewGroup.MarginLayoutParams signUpPwEdit2Params = (ViewGroup.MarginLayoutParams) signUpPwEdit2.getLayoutParams();


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        TextView suguText = (TextView) findViewById(R.id.textView2);

        Button signUpBtn = (Button) findViewById(R.id.signUpBtn);
        ViewGroup.MarginLayoutParams signUpBtnParams = (ViewGroup.MarginLayoutParams) signUpBtn.getLayoutParams();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayout layout1 = (LinearLayout) findViewById(R.id.linearLayout1);
            ViewGroup.MarginLayoutParams layout1Params = (ViewGroup.MarginLayoutParams) layout1.getLayoutParams();

            layout1Params.width = width / 2;

            LinearLayout layout2 = (LinearLayout) findViewById(R.id.linearLayout2);
            ViewGroup.MarginLayoutParams layout2Params = (ViewGroup.MarginLayoutParams) layout2.getLayoutParams();

            layout2Params.width = width / 2;
        }

        titleParams.topMargin = height / 6;

        ViewGroup.MarginLayoutParams radioGroupParams = (ViewGroup.MarginLayoutParams) radioGroup.getLayoutParams();

    }

    public void createProfile(View view) {
        view.startAnimation(buttonClick);
        if (checkIfDataCorrect()) {
            if (checkIfPWCorrect()) {
                Intent createProfilePictureIntent = new Intent(getApplicationContext(), CreateProfilePicture.class);
                startActivity(createProfilePictureIntent);
            }
        }
    }

    public boolean checkIfDataCorrect() {
        boolean done = true;

        if (signUpNameEdit.getText().length() == 0) {
            signUpNameEdit.setHintTextColor(Color.parseColor("#c52512"));
            done = false;
        }

        if (signUpLastEdit.getText().length() == 0) {
            signUpLastEdit.setHintTextColor(Color.parseColor("#c52512"));
            done = false;
        }

        if (signUpUserEdit.getText().length() == 0) {
            signUpUserEdit.setHintTextColor(Color.parseColor("#c52512"));
            done = false;
        }

        if (signUpMailEdit.getText().length() == 0) {
            signUpMailEdit.setHintTextColor(Color.parseColor("#c52512"));
            done = false;
        }

        if (signUpPwEdit1.getText().length() == 0) {
            signUpPwEdit1.setHintTextColor(Color.parseColor("#c52512"));
            done = false;
        }

        if (signUpPwEdit2.getText().length() == 0) {
            signUpPwEdit2.setHintTextColor(Color.parseColor("#c52512"));
            done = false;
        }

        return done;
    }

    public boolean checkIfPWCorrect() {
        boolean done = true;
        if (!signUpPwEdit1.getText().toString().equals(signUpPwEdit2.getText().toString())) {
            signUpPwEdit1.setText("");
            signUpPwEdit2.setText("");
            done = false;
        }
        return done;
    }

    public boolean nameNotCorrect(String name, String pat) {
        Pattern p = Pattern.compile(pat, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        boolean b = m.find();
        if (b) {
            return true;
        } else {
            return false;
        }
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
