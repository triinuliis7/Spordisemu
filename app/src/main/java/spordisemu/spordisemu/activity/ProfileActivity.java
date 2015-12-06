package spordisemu.spordisemu.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import spordisemu.spordisemu.R;
import spordisemu.spordisemu.adapter.RatingListAdapter;
import spordisemu.spordisemu.widget.LoggedIn;
import spordisemu.spordisemu.widget.RoundedImageView;

/**
 * Created by ingrid on 10/25/15.
 */
public class ProfileActivity extends NavigationActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(LoggedIn.firstname + " " + LoggedIn.lastname);

        IntentId = R.id.profiil;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // paneb menüü nupu üles
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        // paneb ülesse navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        RoundedImageView avatar = (RoundedImageView) findViewById(R.id.avatar_icon);
        ImageView genderPic = (ImageView) findViewById(R.id.genderPic);
        if(LoggedIn.gender.equals("1")) {
            genderPic.setBackgroundResource(R.drawable.ic_gender_male_grey600_36dp);
            avatar.setImageDrawable(getDrawable(R.drawable.man));
        } else {
            genderPic.setBackgroundResource(R.drawable.ic_gender_female_grey600_36dp);
            avatar.setImageDrawable(getDrawable(R.drawable.woman));
        }

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(LoggedIn.firstname + " " + LoggedIn.lastname);

        TextView name = (TextView) findViewById(R.id.profilename);
        name.setText(LoggedIn.firstname + " " + LoggedIn.lastname);

        // ratings list
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<String> myDataset =  new ArrayList<>();
        myDataset.add("Esimene rating");
        myDataset.add("Teine rating");
        myDataset.add("Kolmas rating");
        myDataset.add("Neljas rating");
        myDataset.add("Viies rating");


        mAdapter = new RatingListAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
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

}
