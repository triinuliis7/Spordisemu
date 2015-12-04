package spordisemu.spordisemu.widget;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Triinu Liis on 27/11/2015.
 */
public class ParseNotifications  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "FWLLWZk8Adonl3ixkHu71nPUDaM1R2uFcmZJKQA5", "Vtdc1OL9auXX6Gjcavb5wzWzCyKKkAXRbpRlsh1t");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
