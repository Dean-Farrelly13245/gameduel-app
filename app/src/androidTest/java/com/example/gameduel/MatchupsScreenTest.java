package com.example.gameduel;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MatchupsScreenTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void matchupsScreenLoadsAndDisplaysItems() throws InterruptedException {
        onView(withId(R.id.nav_matchups)).perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.txt_title)).check(matches(isDisplayed()));
        onView(withId(R.id.card_game_a)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_vs)).check(matches(isDisplayed()));
        onView(withId(R.id.card_game_b)).perform(scrollTo()).check(matches(isDisplayed()));
    }
}