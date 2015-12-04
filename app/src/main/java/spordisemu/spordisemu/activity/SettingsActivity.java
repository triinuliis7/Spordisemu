package spordisemu.spordisemu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import spordisemu.spordisemu.R;

/**
 * Created by Harry Potter on 23.10.2015.
 */
public class SettingsActivity extends NavigationActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.action_settings);

        IntentId = R.id.action_settings;
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
    }

    public void changeProfile (View view) {
        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(profileIntent);
    }

    public void changePassword (View view) {
        Intent passwordIntent = new Intent(getApplicationContext(), PasswordActivity.class);
        startActivity(passwordIntent);
    }

    public void changeNotifications (View view) {
        Intent notificationsIntent = new Intent(getApplicationContext(), NotificationsActivity.class);
        startActivity(notificationsIntent);
    }

    public void terms (View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
        alert.setMessage(getResources().getString(R.string.termsLong));
        alert.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = alert.create();
        alert11.show();
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
