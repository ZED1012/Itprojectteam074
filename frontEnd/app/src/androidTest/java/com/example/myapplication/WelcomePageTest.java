package com.example.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class WelcomePageTest {

    @Rule
    public ActivityScenarioRule<WelcomePage> activityScenarioRule
            = new ActivityScenarioRule<>(WelcomePage.class);

    @Before
    public void setup() {
        init();
    }
    @After
    public void tearDown() {
        release();
    }
    @Test
    public void testWelcomePageUI() {
        onView(withId(R.id.page1_text)).check(matches(isDisplayed()));
        onView(withId(R.id.page1_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.page1_menu_button)).check(matches(isDisplayed()));
        onView(withId(R.id.page1_start)).check(matches(isDisplayed()));

        onView(withId(R.id.page1_start)).perform(click());
        intended(hasComponent(PersonalDetail.class.getName()));
        pressBack();

        // click menu and check if it is displayed properly
        onView(withId(R.id.page1_menu_button)).perform(click());

    }
}
