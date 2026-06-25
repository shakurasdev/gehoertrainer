package de.htw.gehoertrainer.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import de.htw.gehoertrainer.R
import gehoertrainer.ui.highscore.HighscoreActivity
import org.junit.Test

class FilterActivityTest {

    @Test
    fun cancelFilter_returnsToHighscoreWithoutFilter() {

        ActivityScenario.launch(HighscoreActivity::class.java)

        onView(withId(R.id.btnFilter))
            .perform(click())

        onView(withText("Filter"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnCancel))
            .perform(click())

        onView(withId(R.id.tvActiveFilter))
            .check(matches(withText("kein Filter")))
    }
}