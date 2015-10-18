package spordisemu.spordisemu;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by Kelian on 09/10/2015.
 */
public class Activity_CreateOptionsPicture extends AppCompatActivity{


    private AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.5F);

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_options_picture);

        Intent intent = getIntent();
    }

    public void createSports (View view) {

        view.startAnimation(buttonClick);
        Intent createOptionsSportsIntent = new Intent(getApplicationContext(), Activity_CreateOptionsSports.class);
        startActivity(createOptionsSportsIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
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