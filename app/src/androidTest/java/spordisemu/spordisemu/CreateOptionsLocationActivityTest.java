package spordisemu.spordisemu;

import android.app.KeyguardManager;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by ingrid on 11/8/15.
 */
public class CreateOptionsLocationActivityTest extends ActivityInstrumentationTestCase2<CreateOptionsLocationActivity> {

    private CreateOptionsLocationActivity createOptionsLocationActivity;

    public CreateOptionsLocationActivityTest() {
        super(CreateOptionsLocationActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        createOptionsLocationActivity = getActivity();
        KeyguardManager km = (KeyguardManager) createOptionsLocationActivity.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = km.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();
    }


    public void testClickForwardButton(){

        onView(withId(R.id.button3)).perform(click());
        onView(withText(R.string.pealeht)).check(matches(isDisplayed()));
    }

    /*public void testClickSkipButton(){
        onView(withId(R.id.button4)).perform(click());
        onView(withText(R.string.pealeht)).check(matches(isDisplayed()));
    }*/
}
