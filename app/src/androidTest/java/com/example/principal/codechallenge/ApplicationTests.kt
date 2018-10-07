package com.example.principal.codechallenge

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.AutoCompleteTextView
import com.example.joao.chucknorrisapp.RecyclerViewMatcher
import com.example.principal.codechallenge.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ApplicationTests {

    @Rule @JvmField
    var mActivityRule = ActivityTestRule(MainActivity::class.java)

    fun withRecyclerView(recyclerViewID: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewID)
    }

    @Test
    fun searchSuccessTest(){

        onView(withId(R.id.action_search)).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("g964"), pressImeActionButton())

        try {
            Thread.sleep(20000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withRecyclerView(R.id.challenge_recyclerview).atPosition(0)).check(matches(hasDescendant(withText("Name:"))))

        onView(withRecyclerView(R.id.challenge_recyclerview).atPosition(0)).perform(click())

        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withId(R.id.details_name_label)).check(matches(isDisplayed()))

    }

    @Test
    fun searchFailTest(){

        onView(withId(R.id.action_search)).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("e"), pressImeActionButton())


        try {
            Thread.sleep(15000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withText("Not found")).check(matches(isDisplayed()))
        onView(withText("CLOSE")).perform(click())

    }

    @Test
    fun sortTest(){

        onView(withId(R.id.action_search)).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("kazk"), pressImeActionButton())

        try {
            Thread.sleep(20000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }


        onView(isRoot()).perform(pressBack())

        onView(withRecyclerView(R.id.recyclerview).atPosition(0)).check(matches(hasDescendant(withText("kazk"))))

        onView(withId(R.id.action_sort)).perform(click())

        onView(withRecyclerView(R.id.recyclerview).atPosition(0)).check(matches(hasDescendant(withText("g964"))))

    }



}