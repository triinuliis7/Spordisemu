package spordisemu.spordisemu;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;

import spordisemu.spordisemu.activity.CreatePracticeActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by ingrid on 11/15/15.
 */
public class CreatePracticeActivityTest extends ActivityInstrumentationTestCase2<CreatePracticeActivity> {

    private CreatePracticeActivity createPracticeActivity;

    public CreatePracticeActivityTest() {
        super(CreatePracticeActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        createPracticeActivity = getActivity();
    }

    public void testChangeTextEverythingOK() {

        onView(withId(R.id.sport)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Jalgpall"))).perform(click());

        onView(withId(R.id.raskustase)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Kerge"))).perform(click());

        onView(withId(R.id.day))
                .perform(typeText("10"), closeSoftKeyboard());

        onView(withId(R.id.month))
                .perform(typeText("12"), closeSoftKeyboard());

        onView(withId(R.id.year))
                .perform(typeText("2016"), closeSoftKeyboard());

        onView(withId(R.id.hour))
                .perform(typeText("12"), closeSoftKeyboard());

        onView(withId(R.id.minutes))
                .perform(typeText("00"), closeSoftKeyboard());

        onView(withId(R.id.location))
                .perform(typeText("Tamme staadion"), closeSoftKeyboard());

        onView(withId(R.id.min))
                .perform(typeText("2"), closeSoftKeyboard());

        onView(withId(R.id.max))
                .perform(typeText("10"), closeSoftKeyboard());

        onView(withId(R.id.createPractice)).perform(click());
        onView(withText("Jalgpall")).check(matches(isDisplayed()));
    }

    public void testChangeTextNotFilled() {

        onView(withId(R.id.createPractice)).perform(click());
        onView(withText(R.string.valimataSport)).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.day)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.fill))));
        onView(withId(R.id.month)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.fill))));
        onView(withId(R.id.year)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.fill))));
        onView(withId(R.id.hour)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.fill))));
        onView(withId(R.id.minutes)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.fill))));
        onView(withId(R.id.location)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.fill))));
    }

    public void testFieldsWrong(){

        onView(withId(R.id.sport)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Jalgpall"))).perform(click());

        onView(withId(R.id.raskustase)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Kerge"))).perform(click());

        onView(withId(R.id.day))
                .perform(typeText("10"), closeSoftKeyboard());

        onView(withId(R.id.month))
                .perform(typeText("12"), closeSoftKeyboard());

        onView(withId(R.id.year))
                .perform(typeText("2014"), closeSoftKeyboard());

        onView(withId(R.id.hour))
                .perform(typeText("30"), closeSoftKeyboard());

        onView(withId(R.id.minutes))
                .perform(typeText("75"), closeSoftKeyboard());

        onView(withId(R.id.location))
                .perform(typeText("Tamme staadion"), closeSoftKeyboard());

        onView(withId(R.id.min))
                .perform(typeText("0"), closeSoftKeyboard());

        onView(withId(R.id.max))
                .perform(typeText("0"), closeSoftKeyboard());

        onView(withId(R.id.createPractice)).perform(click());

        onView(withId(R.id.year)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.yearError))));
        onView(withId(R.id.hour)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.timestampError))));
        onView(withId(R.id.minutes)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.timestampError))));
        onView(withId(R.id.min)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.minError))));
        onView(withId(R.id.max)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.minError))));

    }

    public void testMaxSmallerThanMin(){
        onView(withId(R.id.sport)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Jalgpall"))).perform(click());

        onView(withId(R.id.raskustase)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Kerge"))).perform(click());

        onView(withId(R.id.day))
                .perform(typeText("10"), closeSoftKeyboard());

        onView(withId(R.id.month))
                .perform(typeText("12"), closeSoftKeyboard());

        onView(withId(R.id.year))
                .perform(typeText("2016"), closeSoftKeyboard());

        onView(withId(R.id.hour))
                .perform(typeText("12"), closeSoftKeyboard());

        onView(withId(R.id.minutes))
                .perform(typeText("00"), closeSoftKeyboard());

        onView(withId(R.id.location))
                .perform(typeText("Tamme staadion"), closeSoftKeyboard());

        onView(withId(R.id.min))
                .perform(typeText("2"), closeSoftKeyboard());

        onView(withId(R.id.max))
                .perform(typeText("1"), closeSoftKeyboard());

        onView(withId(R.id.createPractice)).perform(click());
        onView(withId(R.id.max)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.maxError))));
    }
}
