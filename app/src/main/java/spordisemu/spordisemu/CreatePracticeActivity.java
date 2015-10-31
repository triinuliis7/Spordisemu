package spordisemu.spordisemu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kelian on 31/10/2015.
 */
public class CreatePracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //sets title of menu
        setTitle(R.string.trenn);

        setContentView(R.layout.activity_create_practice);

    }
}

