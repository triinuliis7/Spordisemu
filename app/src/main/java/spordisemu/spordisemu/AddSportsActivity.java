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
public class AddSportsActivity extends BaseActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle(R.string.lisa_spordiala);

        //for navigation drawer
        getLayoutInflater().inflate(R.layout.activity_add_sports, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

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

}
