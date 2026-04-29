package com.example.gameduel;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class GamesFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void gamesListIsDisplayed() {
        onView(withId(R.id.recycler_games)).check(matches(isDisplayed()));
    }

    @Test
    public void searchBarIsDisplayed() {
        onView(withId(R.id.edit_search)).check(matches(isDisplayed()));
    }

    @Test
    public void searchFilterWorks() {
        onView(withId(R.id.edit_search)).perform(typeText("Grand"));
        onView(withId(R.id.recycler_games)).check(matches(isDisplayed()));
    }

    @Test
    public void clickingGameOpensDetail() throws InterruptedException {
        Thread.sleep(3000);
        onView(withId(R.id.recycler_games))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.txt_detail_title)).check(matches(isDisplayed()));
    }
}