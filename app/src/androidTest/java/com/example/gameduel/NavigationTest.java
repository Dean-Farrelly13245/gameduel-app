package com.example.gameduel;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void userCanNavigateBetweenAllTabs() throws InterruptedException {
        onView(withId(R.id.nav_games)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.spinner_sort)).check(matches(isDisplayed()));

        onView(withId(R.id.nav_matchups)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.card_game_a)).check(matches(isDisplayed()));

        onView(withId(R.id.nav_leaderboard)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.podium_container)).check(matches(isDisplayed()));
    }
}