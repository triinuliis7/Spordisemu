package spordisemu.spordisemu;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Triinu Liis on 01/11/2015.
 */
public class RegistrationActivityTest  extends ActivityInstrumentationTestCase2<RegistrationActivity> {

    private RegistrationActivity registrationActivity;

    public RegistrationActivityTest() {
        super(RegistrationActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        registrationActivity = getActivity();
    }

    public void testChangeTextEverythingOK() {
        // Type text and then press the button.
        onView(withId(R.id.SignUpUserEdit))
                .perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.SignUpUserEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpNameEdit))
                .perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.signUpNameEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.SignUpLastEdit))
                .perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.SignUpLastEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpMailEdit))
                .perform(click(), replaceText("test@mail.ee"), closeSoftKeyboard());
        onView(withId(R.id.signUpMailEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit1))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        onView(withId(R.id.signUpPwEdit1))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit2))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        onView(withId(R.id.signUpPwEdit2))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpBtn)).perform(click());

        // Check if new activity is open
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));
    }
}
