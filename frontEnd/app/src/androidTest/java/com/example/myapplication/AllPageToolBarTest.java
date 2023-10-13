package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AllPageToolBarTest {

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
    public void testToolBarUI() {
        onView(withId(R.id.page1_menu_button)).check(matches(isDisplayed()));
        onView(withId(R.id.page1_menu_button)).perform(click());
        onView(withText("For Volunteers")).perform(click());
        intended(hasComponent(ForVolunteer.class.getName()));

        onView(withId(R.id.all_page_menu_button)).check(matches(isDisplayed()));
        onView(withId(R.id.all_page_menu_button)).perform(click());
        onView(withText("For Leaders")).perform(click());
        intended(hasComponent(ForLeader.class.getName()));

        pressBack();
        intended(hasComponent(ForVolunteer.class.getName()));
        onView(withText("For Volunteers")).perform(swipeUp());

        onView(withId(R.id.all_page_menu_button)).check(matches(isDisplayed()));
        onView(withId(R.id.all_page_menu_button)).perform(click());
        onView(withText("Contact Us")).perform(click());
        intended(hasComponent(ContactUs.class.getName()));

        onView(withId(R.id.all_page_menu_button)).check(matches(isDisplayed()));
        onView(withId(R.id.all_page_menu_button)).perform(click());
        onView(withText("Learn More")).perform(click());
        intended(hasComponent(LearnMore.class.getName()));

        pressBack();
        intended(hasComponent(ContactUs.class.getName()));

    }
}
