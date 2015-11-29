package spordisemu.spordisemu.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import spordisemu.spordisemu.adapter.ExpandableListAdapter;
import spordisemu.spordisemu.R;

/**
 * Created by ingrid on 11/1/15.
 */
public class AddSportsActivity extends NavigationActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sports);
        setTitle(R.string.lisa_spordiala);

        IntentId = R.id.lisa_spordiala;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        // paneb menüü nupu üles
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        // paneb ülesse navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expendableList);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
