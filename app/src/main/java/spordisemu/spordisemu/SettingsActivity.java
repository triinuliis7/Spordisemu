package spordisemu.spordisemu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Harry Potter on 23.10.2015.
 */
public class SettingsActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle(R.string.action_settings);

        //for navigation drawer
        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

    }

}
