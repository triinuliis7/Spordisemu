package spordisemu.spordisemu;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;

import spordisemu.spordisemu.activity.RegistrationActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
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

    /*public void testChangeTextEverythingOK() {
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
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpMailEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit1))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit1))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit2))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit2))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpBtn)).perform(click());

        // Check if new activity is open
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));
    }*/

    public void testChangeTextNotFilled() {

        onView(withId(R.id.signUpBtn)).perform(click());
        onView(withId(R.id.SignUpUserEdit)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.chars4))));
        onView(withId(R.id.signUpNameEdit)).perform(click()).check(matches(hasErrorText(getActivity().getResources().getString(R.string.fill))));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.SignUpLastEdit)).perform(click()).check(matches(hasErrorText(getActivity().getResources().getString(R.string.fill))));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpMailEdit)).perform(click()).check(matches(hasErrorText(getActivity().getResources().getString(R.string.correctEmail))));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit1)).perform(click()).check(matches(hasErrorText(getActivity().getResources().getString(R.string.pwLength))));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit2)).perform(click()).check(matches(hasErrorText(getActivity().getResources().getString(R.string.fill))));

    }

    public void testChangeTextPasswordDontMatch() {
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
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpMailEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit1))
                .perform(click(), replaceText("testtest1"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit1))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit2))
                .perform(click(), replaceText("testtest2"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit2))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpBtn)).perform(click());

        onView(withId(R.id.signUpPwEdit2)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.checkPw))));
    }

    public void testChangeTextSpecialCharactersInUsername() {
        // Type text and then press the button.
        onView(withId(R.id.SignUpUserEdit))
                .perform(typeText("Test."), closeSoftKeyboard());
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
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpMailEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit1))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit1))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit2))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit2))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpBtn)).perform(click());

        onView(withId(R.id.SignUpUserEdit)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.specialCharNum))));
    }

    public void testChangeTextSpecialCharactersInName() {
        // Type text and then press the button.
        onView(withId(R.id.SignUpUserEdit))
                .perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.SignUpUserEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpNameEdit))
                .perform(typeText("Test."), closeSoftKeyboard());
        onView(withId(R.id.signUpNameEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.SignUpLastEdit))
                .perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.SignUpLastEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpMailEdit))
                .perform(click(), replaceText("test@mail.ee"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpMailEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit1))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit1))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit2))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit2))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpBtn)).perform(click());

        onView(withId(R.id.signUpNameEdit)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.specialChar))));
    }

    public void testChangeTextSpecialCharactersInLast() {
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
                .perform(typeText("Test."), closeSoftKeyboard());
        onView(withId(R.id.SignUpLastEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpMailEdit))
                .perform(click(), replaceText("test@mail.ee"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpMailEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit1))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit1))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit2))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit2))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpBtn)).perform(click());

        onView(withId(R.id.SignUpLastEdit)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.specialChar))));
    }

    public void testChangeTextIncorrectEmail() {
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
                .perform(click(), replaceText("testmailee"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpMailEdit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit1))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit1))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpPwEdit2))
                .perform(click(), replaceText("testtest"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signUpPwEdit2))
                .check(matches(isDisplayed()));
        onView(withId(R.id.signUpBtn)).perform(click());

        onView(withId(R.id.signUpMailEdit)).check(matches(hasErrorText(getActivity().getResources().getString(R.string.correctEmail))));
    }
}
