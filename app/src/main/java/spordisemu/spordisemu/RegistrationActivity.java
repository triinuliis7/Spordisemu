package spordisemu.spordisemu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class RegistrationActivity extends AppCompatActivity {

    public final static String apiURL = "http://private-6358e-spordisemu1.apiary-mock.com";

    static EditText signUpNameEdit;
    static EditText signUpLastEdit;

    static EditText signUpUserEdit;

    static EditText signUpMailEdit;

    static EditText signUpPwEdit1;
    static EditText signUpPwEdit2;

    static RadioButton signUpGenderM;

    public final static String EXTRA_MESSAGE = "";
    public static boolean loggedIn = false;

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

        signUpGenderM = (RadioButton) findViewById(R.id.r1);

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

        titleParams.topMargin = height / 8;

        ViewGroup.MarginLayoutParams radioGroupParams = (ViewGroup.MarginLayoutParams) radioGroup.getLayoutParams();

    }

    public void createProfile(View view) {
        view.startAnimation(buttonClick);
        int gender;
        if (signUpGenderM.isChecked()) {
            gender = 1;
        } else {
            gender = 0;
        }
        if (checkIfDataCorrect()) {
            JSONObject json = new JSONObject();
            try {
                json.put("firstname", signUpNameEdit.getText().toString());
                json.put("lastname", signUpLastEdit.getText().toString());
                json.put("username", signUpUserEdit.getText().toString());
                json.put("gender", gender);
                json.put("email", signUpMailEdit.getText().toString());
                json.put("password", signUpPwEdit1.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            postJson(json);
        }
    }

    public boolean checkIfDataCorrect() {
        boolean done = true;
        if (checkIfEmpty(signUpUserEdit)) {
            done = false;
        }
        if (checkIfEmpty(signUpNameEdit)) {
            done = false;
        }
        if (checkIfEmpty(signUpLastEdit)) {
            done = false;
        }
        if (checkIfEmpty(signUpMailEdit)) {
            done = false;
        }
        if (checkIfEmpty(signUpPwEdit1)) {
            done = false;
        }
        if (checkIfEmpty(signUpPwEdit2)) {
            done = false;
        }
        if (checkIfPWNotCorrect()) {
            done = false;
        }
        if (emailNotCorrect(signUpMailEdit)) {
            done = false;
        }
        if (nameNotCorrect("[^a-z0-9]", signUpUserEdit)) {
            done = false;
        }
        if (nameNotCorrect("[^a-z]", signUpNameEdit)) {
            done = false;
        }
        if (nameNotCorrect("[^a-z]", signUpLastEdit)) {
            done = false;
        }
        return done;
    }

    public boolean checkIfEmpty(EditText name) {
        if (name.getText().length() == 0) {
            name.setError(getResources().getString(R.string.fill));
            return true;
        }
        return false;
    }

    public boolean checkIfPWNotCorrect() {
        boolean done = false;
        if (!signUpPwEdit1.getText().toString().equals(signUpPwEdit2.getText().toString())) {
            signUpPwEdit1.setError(getResources().getString(R.string.checkPw));
            signUpPwEdit2.setError(getResources().getString(R.string.checkPw));
            signUpPwEdit1.setText("");
            signUpPwEdit2.setText("");
            done = true;
        }
        if (signUpPwEdit1.getText().length() < 6) {
            signUpPwEdit1.setError(getResources().getString(R.string.pwLength));
            done = true;
        }
        return done;
    }

    public boolean nameNotCorrect(String pat, EditText name) {
        Pattern p = Pattern.compile(pat, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name.getText().toString());
        boolean b = m.find();
        if (b) {
            if (name == signUpUserEdit) {
                name.setError(getResources().getString(R.string.specialCharNum));
            }
            if (name == signUpNameEdit) {
                name.setError(getResources().getString(R.string.specialChar));
            }
            if (name == signUpLastEdit) {
                name.setError(getResources().getString(R.string.specialChar));
            }
        }
        if (name == signUpUserEdit && name.getText().length() < 4) {
            name.setError(getResources().getString(R.string.chars4));
            return true;
        }
        return b;
    }

    public boolean emailNotCorrect(EditText email) {
        boolean done;
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        done = !pattern.matcher(email.getText()).matches();
        if (done) {
            email.setError(getResources().getString(R.string.correctEmail));
        }
        return done;
    }

    public void postJson(JSONObject json) {
        String apiURL = getResources().getString(R.string.apiUrl);
        String urlString = apiURL + "/users";
        new CallAPI().execute(urlString, json.toString());
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder alert = new AlertDialog.Builder(RegistrationActivity.this);
            alert.setMessage(getResources().getString(R.string.backRegistration));
            alert.setNegativeButton("Jah",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent MainIntent = new Intent(getApplicationContext(), MainActivity.class);
                            MainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(MainIntent);
                        }
                    });
            alert.setPositiveButton("Ei",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = alert.create();
            alert11.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class CallAPI extends AsyncTask<String, String, String> {

        ProgressDialog dialog = new ProgressDialog(RegistrationActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(getResources().getString(R.string.wait));
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            String request = params[1];
            String response;

            StringBuffer jsonResponse = new StringBuffer();

            // HTTP POST
            try {
                String line;
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
                writer.write(request);
                writer.close();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                while ((line = br.readLine()) != null) {
                    jsonResponse.append(line);
                }
                br.close();

                urlConnection.disconnect();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return request;
            }
            response = jsonResponse.toString();
            return response;
        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
            Intent createOptionsPictureIntent = new Intent(getApplicationContext(), CreateOptionsPictureActivity.class);
            try {
                JSONObject json = new JSONObject(result);
                createOptionsPictureIntent.putExtra("user_id", json.getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(createOptionsPictureIntent);
        }
    }

}
