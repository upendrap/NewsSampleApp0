package com.example.newssampleapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun testSuccessfulState() {
        val repository: FakeNewsRepository =
            (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApp).newsRepository as FakeNewsRepository
        repository.setSuccessfulResponse()

        activityRule.launchActivity(null)

        onView(withId(R.id.rvNewsItems)).check(matches(isDisplayed()))
        onView(withId(R.id.flLoading)).check(matches(not(isDisplayed())))
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(doesNotExist())
    }

    @Test
    fun testErrorState() {
        val repository: FakeNewsRepository =
            (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApp).newsRepository as FakeNewsRepository
        repository.setErrorResponse()

        activityRule.launchActivity(null)

        onView(withId(R.id.flLoading)).check(matches(not(isDisplayed())))
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.error_network)))
    }
}