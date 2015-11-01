package spordisemu.spordisemu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ExpandableListView;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ingrid on 11/1/15.
 */
public class AddSportsActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle(R.string.lisa_spordiala);

        setContentView(R.layout.activity_add_sports);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expendableList);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Pallimängud");
        listDataHeader.add("Talisport");
        listDataHeader.add("Murumängud");

        // Adding child data
        List<String> pallimangud = new ArrayList<String>();
        pallimangud.add("Jalgpall");
        pallimangud.add("Korvpall");
        pallimangud.add("Sulgpall");

        List<String> talisport = new ArrayList<String>();
        talisport.add("Uisutamine");
        talisport.add("Suusatamine");
        talisport.add("Jäähoki");

        List<String> murumangud = new ArrayList<String>();
        murumangud.add("Golf");
        murumangud.add("Pentang");

        listDataChild.put(listDataHeader.get(0), pallimangud); // Header, Child data
        listDataChild.put(listDataHeader.get(1), talisport);
        listDataChild.put(listDataHeader.get(2), murumangud);
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

        switch(id) {
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
