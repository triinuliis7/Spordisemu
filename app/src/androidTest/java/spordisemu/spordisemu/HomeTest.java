package spordisemu.spordisemu;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.support.test.espresso.Espresso;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Before;

import spordisemu.spordisemu.activity.HomeActivity;

/**
 * Created by ingrid on 12/6/15.
 */
public class HomeTest extends ActivityInstrumentationTestCase2<HomeActivity> {


    private HomeActivity homeActivity;

    public HomeTest() {
        super(HomeActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        homeActivity = getActivity();
    }

    //TODO
    public void testClickOnSport(){
        onView(withId(R.id.sports_list)).perform(click());
        onView(withText("Jalgpall")).check(matches(withText("Jalgpall")));
    }

    public void testClickOnAddPractice(){
        onView(withId(R.id.createPractice)).perform(click());
        onView(withId(R.id.raskustaseText)).check(matches(withId(R.id.raskustaseText)));
    }

}
