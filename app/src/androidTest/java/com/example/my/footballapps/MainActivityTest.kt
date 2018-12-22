package com.example.my.footballapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.my.footballapps.R.id.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testAppBehavior(){

        Thread.sleep(2000)
        onView(withId(rv_last_match )).check(matches(isDisplayed()))
        onView(withId(rv_last_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(13))
        onView(withId(rv_last_match)).perform(RecyclerViewActions
            .actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        Thread.sleep(1000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite")).check(ViewAssertions.matches(isDisplayed()))

        pressBack()

        onView(withId(view_pager)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(view_pager)).perform(swipeLeft())

        onView(withId(next_match_spinner)).check(ViewAssertions.matches(isDisplayed())).perform(click())
        onView(withText("Spanish La Liga")).perform(click())

        Thread.sleep(2000)
        onView(withId(rv_next_match)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(rv_next_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))
        onView(withId(rv_next_match)).perform(RecyclerViewActions
            .actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click()))

        Thread.sleep(1000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite")).check(ViewAssertions.matches(isDisplayed()))

        pressBack()

        onView(withId(bottom_navigation)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(teams)).perform(click())

        onView(withId(spinner_teams)).check(ViewAssertions.matches(isDisplayed())).perform(click())
        onView(withText("Italian Serie A")).perform(click())

        Thread.sleep(2000)
        onView(withId(listTeam)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(listTeam)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(6))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click()))

        Thread.sleep(1000)
        onView(withId(viewpager_detail_team)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(viewpager_detail_team)).perform(swipeLeft())

        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite")).check(ViewAssertions.matches(isDisplayed()))

        pressBack()

        onView(withId(bottom_navigation)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(favorites)).perform(click())

        Thread.sleep(1000)
        onView(
            allOf(withTagValue(`is`("fav_match" as Any)),
                withId(listFav))).check(ViewAssertions.matches(isDisplayed()))
        onView(allOf(withTagValue(`is`("fav_match" as Any)),
            withId(listFav))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        Thread.sleep(1000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed from favorite")).check(ViewAssertions.matches(isDisplayed()))

        pressBack()

        onView(withId(view_pager_favorite)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(view_pager_favorite)).perform(swipeLeft())

        Thread.sleep(1000)
        onView(
            allOf(withTagValue(`is`("fav_team" as Any)),
                withId(listFav))).check(ViewAssertions.matches(isDisplayed()))
        onView(allOf(withTagValue(`is`("fav_team" as Any)),
            withId(listFav))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed from favorite")).check(ViewAssertions.matches(isDisplayed()))

    }
}