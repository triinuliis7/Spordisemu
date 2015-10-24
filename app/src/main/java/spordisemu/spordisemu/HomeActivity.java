package spordisemu.spordisemu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Triinu Liis on 11/10/2015.
 */
public class HomeActivity extends AppCompatActivity{

    public final static String apiURL = "http://private-6358e-spordisemu1.apiary-mock.com";


    String[] sportsArray = {"Jalgpall", "Korvpall", "Sulgpall", "Korvpall"};


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle("Pealeht");

        setContentView(R.layout.activity_home);


        ListView sportsListView = (ListView) findViewById(R.id.sports_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this,
                android.R.layout.simple_list_item_1, sportsArray);
        sportsListView.setAdapter(arrayAdapter);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
