package spordisemu.spordisemu.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import spordisemu.spordisemu.R;

/**
 * Created by ingrid on 11/18/15.
 */


public class NavigationActivity extends AppCompatActivity {

    protected DrawerLayout mDrawerLayout;
    // IntentId hoiab endas hetkel avatud lehe id'd
    protected int IntentId;

        // navigation view itemid ja mida tehakse, kui nende peale vajutada
        protected void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if(menuItem.isChecked()) menuItem.setChecked(false);
                        else menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch(menuItem.getItemId()) {
                            case R.id.pealeht:
                                // kui see leht pole juba lahti
                                if (IntentId != R.id.pealeht) {
                                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                }
                                return true;
                            case R.id.profiil:
                                if (IntentId != R.id.profiil) {
                                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                }
                                return true;
                            case R.id.lisa_spordiala:
                                if(IntentId != R.id.lisa_spordiala) {
                                    startActivity(new Intent(getApplicationContext(), AddSportsActivity.class));
                                }
                                return true;
                            case R.id.sobrad:
                                if(IntentId != R.id.sobrad) {
                                    startActivity(new Intent(getApplicationContext(), FriendsActivity.class));
                                }
                                return true;
                            case R.id.action_settings:
                                if(IntentId != R.id.action_settings) {
                                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                                }
                                return true;
                            case R.id.logivalja:
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                return true;
                            default:
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                return true;
                        }
                    }
                });
    }


}