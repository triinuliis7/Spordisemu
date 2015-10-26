package spordisemu.spordisemu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by ingrid on 10/25/15.
 */
public class PracticeViewActivity extends AppCompatActivity{



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_practice_view);

        String sports = getIntent().getExtras().getString("sports");
        setTitle(sports);

        String date = getIntent().getExtras().getString("date");
        String location = getIntent().getExtras().getString("location");

        TextView tv = (TextView) findViewById(R.id.textView5);
        tv.setText(date);

        TextView tv2 = (TextView) findViewById(R.id.textView7);
        tv2.setText(location);

        //setContentView(tv);
        //setContentView(tv2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch(item.getItemId()) {
            case R.id.pealeht:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.profiil:
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.sobrad:
                Intent friendsIntent = new Intent(getApplicationContext(), FriendsActivity.class);
                startActivity(friendsIntent);
                break;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.logivalja:
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Midagi läks valesti :(",
                        Toast.LENGTH_SHORT).show();
                break;
        }

        //Return false to allow normal menu processing to proceed,
        //true to consume it here.
        return false;
    }
}
