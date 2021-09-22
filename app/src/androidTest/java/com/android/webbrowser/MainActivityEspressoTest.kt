package com.android.webbrowser

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms.findElement
import androidx.test.espresso.web.webdriver.Locator
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityEspressoTest {
    private lateinit var stringToBetyped: String


    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        stringToBetyped = "https://developer.android.com"
    }

    @Test
     fun checkEditTextTyping(){
        onView(withId(R.id.webUrlEditText))
            .perform(typeText(stringToBetyped), closeSoftKeyboard())
        onView(withId(R.id.goButton)).perform(click())
        onView(withId(R.id.webUrlEditText)).check(matches(withText(stringToBetyped)));
    }

    @Test
    fun checkWebView(){
        onView(withId(R.id.webUrlEditText))
            .perform(typeText(stringToBetyped), closeSoftKeyboard())
        onView(withId(R.id.goButton)).perform(click())

        onWebView().withElement(findElement(Locator.TAG_NAME, "header"))

    }

}