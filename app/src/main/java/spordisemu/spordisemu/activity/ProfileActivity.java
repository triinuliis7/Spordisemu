package spordisemu.spordisemu.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import spordisemu.spordisemu.R;
import spordisemu.spordisemu.widget.RoundedImageView;

/**
 * Created by ingrid on 10/25/15.
 */
public class ProfileActivity extends NavigationActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
        avatar.setImageDrawable(getDrawable(R.drawable.woman));
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
