package spordisemu.spordisemu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by Triinu Liis on 11/10/2015.
 */
public class HomeActivity extends AppCompatActivity{

    public final static String apiURL = "http://private-6358e-spordisemu1.apiary-mock.com";

    ListView sports_list;
    String[] sportsArray = {"Jalgpall", "Korvpall", "Sulgpall", "Korvpall"};
    String[] dateArray = {"30. oktoober", "3. november", "5. nomveber", "5. november"};


    Integer[] imgid={
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //sets title of menu
        setTitle("Pealeht");

        setContentView(R.layout.activity_home);


        CustomListAdapter adapter = new CustomListAdapter(this, sportsArray, dateArray, imgid);
        sports_list=(ListView)findViewById(R.id.sports_list);
        sports_list.setAdapter(adapter);

        sports_list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = sportsArray[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
