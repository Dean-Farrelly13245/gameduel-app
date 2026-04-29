package com.example.gameduel;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.espresso.contrib.RecyclerViewActions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class GameDetailTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void clickingGameOpensDetailScreen() throws InterruptedException {
        onView(withId(R.id.nav_games)).perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.recycler_games))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(1500);

        onView(withId(R.id.img_cover)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_detail_title)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_detail_genre)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_detail_platform)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_detail_release_year)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_detail_wins)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_detail_losses)).check(matches(isDisplayed()));
    }
}