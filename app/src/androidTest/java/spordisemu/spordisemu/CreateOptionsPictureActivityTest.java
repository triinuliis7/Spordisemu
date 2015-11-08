package spordisemu.spordisemu;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ingrid on 11/8/15.
 */
public class CreateOptionsPictureActivityTest extends ActivityInstrumentationTestCase2<CreateOptionsPictureActivity> {

    private CreateOptionsPictureActivity createOptionsPictureActivity;

    public CreateOptionsPictureActivityTest() {
        super(CreateOptionsPictureActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        createOptionsPictureActivity = getActivity();
    }

    public void testClickForwardButton(){

        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));
    }

    public void testClickSkipbutton(){
        onView(withId(R.id.button4)).perform(click());
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));
    }
}
