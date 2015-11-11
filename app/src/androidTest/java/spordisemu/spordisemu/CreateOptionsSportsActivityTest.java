package spordisemu.spordisemu;

import android.app.KeyguardManager;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;


import org.junit.Before;

/**
 * Created by ingrid on 11/1/15.
 */
public class CreateOptionsSportsActivityTest extends ActivityInstrumentationTestCase2<CreateOptionsSportsActivity> {

    private CreateOptionsSportsActivity createOptionsSportsActivity;

    public CreateOptionsSportsActivityTest() {
        super(CreateOptionsSportsActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        createOptionsSportsActivity = getActivity();
    }

    public void testSportAndLevelChosen(){

        //chooses sports and level
        onView(withId(R.id.sport)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Jalgpall"))).perform(click());

        onView(withId(R.id.raskustase)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Kerge"))).perform(click());

        //clicks on button "Lisa" and ckecks if there is correct text
        onView(withId(R.id.button2)).perform(click());
        onView(allOf(withId(R.id.textView6), withText("Lisatud spordiala:"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.sportsText), withText("Jalgpall: Kerge"))).check(matches(isDisplayed()));

        //"Edasi" button
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));

    }
    public void testSportOnlyChosen(){
        onView(withId(R.id.sport)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Jalgpall"))).perform(click());

        //clicks on button "Lisa" and ckecks if alert box is coming up
        onView(withId(R.id.button2)).perform(click());
        onView(withText(R.string.valimataSport)).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());

        //"Edasi" button
        onView(withId(R.id.button3)).perform(click());
        onView(withText(R.string.valimataSport)).check(matches(isDisplayed()));

    }
    public void testLevelOnlyChosen(){
        onView(withId(R.id.raskustase)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Kerge"))).perform(click());

        //clicks on button "Lisa" and ckecks if alert box is coming up
        onView(withId(R.id.button2)).perform(click());
        onView(withText(R.string.valimataSport)).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());

        //"Edasi" button
        onView(withId(R.id.button3)).perform(click());
        onView(withText(R.string.valimataSport)).check(matches(isDisplayed()));
    }
    public void testNothingChosen(){

        //clicks on button "Lisa" and ckecks if alert box is coming up
        onView(withId(R.id.button2)).perform(click());
        onView(withText(R.string.valimataSport)).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());

        //"Edasi" button
        onView(withId(R.id.button3)).perform(click());
        onView(withText(R.string.valimataSport)).check(matches(isDisplayed()));
    }
}
