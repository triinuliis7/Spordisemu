package spordisemu.spordisemu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by ingrid on 10/25/15.
 */
public class FriendsActivity extends BaseActivity{

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //for navigation drawer
        getLayoutInflater().inflate(R.layout.activity_friends, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

    }
}
